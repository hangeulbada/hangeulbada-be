package com.hangeulbada.domain.assignment.service;

import com.hangeulbada.domain.assignment.dto.AssignmentDTO;
import com.hangeulbada.domain.assignment.dto.ScoreDTO;
import com.hangeulbada.domain.assignment.dto.SpecificAssignmentDTO;
import com.hangeulbada.domain.assignment.dto.SpecificCompareDTO;
import com.hangeulbada.domain.assignment.entity.Assignment;
import com.hangeulbada.domain.assignment.repository.AssignmentRepository;
import com.hangeulbada.domain.ocr.dto.OCRRequest;
import com.hangeulbada.domain.ocr.service.OCRService;
import com.hangeulbada.domain.user.service.UserService;
import com.hangeulbada.domain.workbookset.dto.QuestionResponseDto;
import com.hangeulbada.domain.workbookset.service.QuestionService;
import com.hangeulbada.domain.workbookset.service.WorkbookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AssignmentService {
    private final AssignmentRepository assignmentRepository;
    private final QuestionService questionService;
    private final WorkbookService workbookService;
    private final UserService userService;
    private final OCRService ocrService;
    private final ModelMapper mapper;

    public SpecificAssignmentDTO getAssignment(String studentId, String workbookId){
        Assignment assignment = assignmentRepository.findLatestByStudentIdAndWorkbookId(studentId, workbookId);
        List<String> questionIds = workbookService.getQuestionIdsByWorkbookId(workbookId);

        List<SpecificCompareDTO> compareDTOS = new ArrayList<>();

        for (int i=0; i<questionIds.size(); i++){
            String questionContent = questionService.getQuestionById(workbookId, questionIds.get(i)).getContent() .replace('.',' ').strip()  ;
            String studentAnswer = assignment.getContent().get(i+1);
            compareDTOS.add(SpecificCompareDTO.builder()
                    .number(String.valueOf(i+1))
                    .answer(questionContent)
                    .studentAnswer(studentAnswer)
                    .isCorrect(questionContent.equals(studentAnswer))
                    .build());
        }


        return SpecificAssignmentDTO.builder()
                .imgS3Url(assignment.getImgS3Url())
                .score(assignment.getScore())
                .answers(compareDTOS)
                .studentName(userService.getUserById(studentId).getName())
                .build();
    }

    public List<ScoreDTO> requestOCR(OCRRequest ocrRequest, String studentId){
        List<String> ocrText = ocrService.startOCR(ocrRequest.getImageName());
        List<ScoreDTO> scores = this.getScores(ocrRequest.getWorkbookId(), ocrText);
        log.info("ocrText: {}", ocrText);
        log.info("scores: {}", scores);
        this.saveScores(ocrRequest.getImageName(), studentId, ocrRequest.getWorkbookId(), scores, ocrText);
        return scores;
    }
    public void saveScores(String imgS3url, String studentId, String workbookId, List<ScoreDTO> scores, List<String> ocrText){
        Map<Integer, String> content = new HashMap<>();
        for (String text : ocrText) {
            String[] parts = text.split("\\.", 2);
            if (parts.length == 2) {
                try {
                    Integer number = Integer.parseInt(parts[0].trim());
                    String contentText = parts[1].trim();
                    content.put(number, contentText);
                } catch (NumberFormatException e) {
                    log.error("Error parsing question number: '{}'", parts[0], e);
                }
            } else {
                log.warn("Unexpected OCR text format: '{}'", text);
            }
        }
        long totalScore = 100;
        int questionCount = workbookService.getQuestionIdsByWorkbookId(workbookId).size();
        long scorePerQuestion = !scores.isEmpty() ? 100 / questionCount : 0;

        if (scores.size()!=questionCount){
            totalScore-=scorePerQuestion*(questionCount-scores.size());
        }
        for (ScoreDTO score : scores) {
            if (!score.isCorrect()) {
                totalScore -= scorePerQuestion;
            }
        }

        AssignmentDTO assignmentDTO = AssignmentDTO.builder()
                .imgS3Url(imgS3url)
                .studentId(studentId)
                .workbookId(workbookId)
                .content(content)
                .score(String.valueOf(totalScore))
                .submitDate(LocalDateTime.now())
                .build();

        assignmentRepository.save(mapper.map(assignmentDTO, Assignment.class));
    }

    public List<ScoreDTO> getScores(String workbookId, List<String> ocrText) {
        List<QuestionResponseDto> questionDtos = questionService.getQuestionsByWorkbookId(workbookId);
        List<String> questions = questionDtos.stream().map(QuestionResponseDto::getContent).toList();
        List<ScoreDTO> scores = new ArrayList<>();

        for (int i = 0; i < questions.size() && i < ocrText.size(); i++) {
            String text = ocrText.get(i);
            String[] parts = text.split("\\.", 2);
            if (parts.length == 2) {
                try {
                    int number = Integer.parseInt(parts[0].trim());
                    String content = parts[1].strip();

                    log.info("content"+content+ "question"+questions.get(number-1).replace('.',' ').strip());

                    boolean isCorrect = questions.get(number-1).replace('.',' ').strip().equals(content);
                    scores.add(ScoreDTO.builder()
                            .number(number)
                            .isCorrect(isCorrect)
                            .build());
                } catch (NumberFormatException e) {
                    log.error("Error parsing question number: '{}'", parts[0], e);
                }
            } else {
                log.warn("Unexpected OCR text format: '{}'", text);
            }
        }
        return scores;
    }
}
