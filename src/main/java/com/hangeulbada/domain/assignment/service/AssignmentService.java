package com.hangeulbada.domain.assignment.service;

import com.hangeulbada.domain.assignment.dto.AssignmentDTO;
import com.hangeulbada.domain.assignment.dto.ScoreDTO;
import com.hangeulbada.domain.assignment.entity.Assignment;
import com.hangeulbada.domain.assignment.repository.AssignmentRepository;
import com.hangeulbada.domain.ocr.dto.OCRRequest;
import com.hangeulbada.domain.ocr.service.OCRService;
import com.hangeulbada.domain.workbookset.dto.QuestionDto;
import com.hangeulbada.domain.workbookset.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
    private final OCRService ocrService;
    private final ModelMapper mapper;

    public List<ScoreDTO> requestOCR(OCRRequest ocrRequest, String studentId){
        List<String> ocrText = ocrService.startOCR(ocrRequest.getImageName());
        List<ScoreDTO> scores = this.getScores(ocrRequest.getWorkbookId(), ocrText);
        log.info("ocrText: {}", ocrText);
        log.info("scores: {}", scores);
        this.saveScores(studentId, ocrRequest.getWorkbookId(), scores, ocrText);
        return scores;
    }
    public void saveScores(String studentId, String workbookId, List<ScoreDTO> scores, List<String> ocrText){
        Map<Integer, String> content = new HashMap<>();
        for (String text : ocrText) {
            String number = text.split("\\.")[0].strip();
            String contentText = text.split("\\.")[1].strip();
            content.put(Integer.parseInt(number), contentText);
        }
        long totalScore = 100;
        long scoreperQuestion = 100 / scores.size();

        for (ScoreDTO score: scores){
            if (!score.isCorrect()){
                totalScore-=scoreperQuestion;
            }
        }

        AssignmentDTO assignmentDTO = AssignmentDTO.builder()
                .studentId(studentId)
                .workbookId(workbookId)
                .content(content)
                .score(String.valueOf(totalScore))
                .build();

        assignmentRepository.save(mapper.map(assignmentDTO, Assignment.class));
    }

    public List<ScoreDTO> getScores(String workbookId, List<String> ocrText) {
        List<QuestionDto> questionDtos = questionService.getQuestionsByWorkbookId(workbookId);
        List<String> questions = questionDtos.stream().map(QuestionDto::getContent).toList();
        List<ScoreDTO> scores = new ArrayList<>();


        // OCR 결과와 문제를 비교하여 점수를 계산
        for (int i=0;i<questions.size();i++){
            String question = questions.get(i);
            String text = ocrText.get(i);
            String number = text.split("\\.")[0].strip();
            String content = text.split("\\.")[1].strip();
            scores.add(ScoreDTO.builder()
                    .number(Integer.parseInt(number))
                    .isCorrect(question.equals(content))
                    .build());
        }
        return scores;
    }
}
