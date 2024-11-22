package com.hangeulbada.domain.workbookset.service.impl;

import com.hangeulbada.domain.externalapi.service.ApiServiceImpl;
import com.hangeulbada.domain.tts.service.TTSService;
import com.hangeulbada.domain.workbookset.dto.*;
import com.hangeulbada.domain.workbookset.entity.Question;
import com.hangeulbada.domain.workbookset.entity.Tag;
import com.hangeulbada.domain.workbookset.entity.Workbook;
import com.hangeulbada.domain.workbookset.exception.NotAuthorizedException;
import com.hangeulbada.domain.workbookset.exception.ResourceNotFoundException;
import com.hangeulbada.domain.workbookset.exception.WorkbookException;
import com.hangeulbada.domain.workbookset.repository.QuestionRepository;
import com.hangeulbada.domain.workbookset.repository.WorkbookRepository;
import com.hangeulbada.domain.workbookset.service.QuestionService;
import com.hangeulbada.domain.workbookset.service.WorkbookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final WorkbookRepository workbookRepository;
    private final ModelMapper mapper;
    private final TTSService ttsService;
    private final ApiServiceImpl apiService;
    private final WorkbookService workbookService;

    @Override
    public List<QuestionDto> getQuestionsByWorkbookId(String workbookId) {
        Workbook workbook = workbookRepository.findById(workbookId)
                .orElseThrow(()-> new ResourceNotFoundException("Workbook","id", workbookId));

        List<String> questionIds = workbook.getQuestionIds();
        List<QuestionDto> questionList = new ArrayList<>();
        for (String q: questionIds) {
            Question question = questionRepository.findById(q)
                    .orElseThrow(()-> new ResourceNotFoundException("Question","id", q));
            questionList.add(mapper.map(question, QuestionDto.class));
        }

        return questionList;
    }

    @Override
    public String createAiGeneratedQuestions(ClaudeRequestDto claudeRequestDto) {
        String genQuestionsString = apiService.postToClaude(claudeRequestDto);
        return genQuestionsString;
    }

    @Override
    public void deleteQuestion(String teacherId, String questionId) {
        Question question= questionRepository.findById(questionId)
                .orElseThrow(()-> new ResourceNotFoundException("Question","id", questionId));
        if (!question.getTeacherId().equals(teacherId))
            throw new NotAuthorizedException("작성자만 삭제할 수 있습니다.");

        //모든 workbook에서 해당 question 지움
        for(Workbook w : workbookRepository.findAll()){
            if(w.getQuestionIds().contains(questionId)) {
                deleteQuestionFromWorkbook(w.getTeacherId(), w.getId(), questionId);
                workbookService.updateWorkbookDifficulty(w.getId());
            }
        }
        ttsService.deleteFileFromS3(question.getAudioFilePath());
        questionRepository.deleteById(questionId);
    }

    @Override
    public List<QuestionDto> getAllQuestions(String teacherId) {
        List<Question> questionList = questionRepository.findByTeacherId(teacherId);
        return questionList.stream().map(question -> mapper.map(question, QuestionDto.class)).collect(Collectors.toList());
    }

    @Override
    public QuestionDto createQuestion(String teacherId, String workbookId, QuestionRequestDto questionRequestDto) {
        Workbook w = workbookRepository.findById(workbookId)
                .orElseThrow(()-> new ResourceNotFoundException("Workbook","id", workbookId));
        String audioFilePath = ttsService.tts(questionRequestDto.getContent());

        QuestionDto questionDto = QuestionDto.builder()
                .content(questionRequestDto.getContent().replace('.', ' ').strip())
                .teacherId(teacherId)
                .difficulty(questionRequestDto.getDifficulty())
                .tags(questionRequestDto.getTags())
                .audioFilePath(audioFilePath)
                .build();
        Question newQuestion = questionRepository.save(mapper.map(questionDto, Question.class));

        if(w.getQuestionIds()==null){
            w.setQuestionIds(new ArrayList<>());
        }
        w.getQuestionIds().add(newQuestion.getId());
        workbookRepository.save(w);
        workbookService.updateWorkbookDifficulty(w.getId());
        return mapper.map(newQuestion, QuestionDto.class);
    }

    @Override
    public WorkbookDto getQuestionsToCreate(String teacherId, String workbookId, List<QuestionRequestDto> questions) {
        Workbook w = workbookRepository.findById(workbookId)
                .orElseThrow(()-> new ResourceNotFoundException("Workbook","id", workbookId));
        List<String> questionIds = new ArrayList<>();
        double diffSum = 0.0;
        for(QuestionRequestDto q : questions){
            QuestionDto questionDto = QuestionDto.builder().teacherId(teacherId).content(q.getContent()).difficulty(q.getDifficulty()).tags(q.getTags()).audioFilePath(ttsService.tts(q.getContent())).build();
            Question newQuestion = questionRepository.save(mapper.map(questionDto, Question.class));
            questionIds.add(newQuestion.getId());
            diffSum += newQuestion.getDifficulty();
        }
        w.setQuestionIds(questionIds);
        if(diffSum<1|| questions.isEmpty()) w.setDifficulty(0);
        else w.setDifficulty((int)Math.round(diffSum/questions.size()));
        workbookRepository.save(w);
        return mapper.map(w, WorkbookDto.class);
    }

    @Override
    public WorkbookDto getAlreadyExistingQuestionToAdd(String teacherId, String workbookId, QuestionRequestListDto questionIds) {
        Workbook w = workbookRepository.findById(workbookId)
                .orElseThrow(()-> new ResourceNotFoundException("Workbook","id", workbookId));
        List<String> qIds = new ArrayList<>();
        double diffSum = 0.0;
        for(String qId : questionIds.getContent()){
            Optional<Question> question = questionRepository.findById(qId);
            if (question.isPresent()){
                qIds.add(qId);
                diffSum+=question.get().getDifficulty();
            }
        }
        w.setQuestionIds(qIds);
        log.info("qid들 저장 완료");
        if(diffSum<1|| qIds.isEmpty()) w.setDifficulty(0);
        else w.setDifficulty((int)Math.round(diffSum/qIds.size()));
        workbookRepository.save(w);
        return mapper.map(w, WorkbookDto.class);
    }

    @Override
    public QuestionDto getQuestionById(String workbookId, String questionId) {
        Workbook workbook = workbookRepository.findById(workbookId)
                .orElseThrow(()-> new ResourceNotFoundException("Workbook","id", workbookId));

        Question question = questionRepository.findById(questionId)
                .orElseThrow(()-> new ResourceNotFoundException("Question","id", questionId));

        if(workbook.getQuestionIds().stream().noneMatch(q -> q.equals(question.getId()))){
            throw new WorkbookException(HttpStatus.BAD_REQUEST, "세트에 해당 문제가 존재하지 않습니다.");
        }
        return mapper.map(question,QuestionDto.class);
    }

    @Override
    public void deleteQuestionFromWorkbook(String teacherId, String workbookId, String questionId) {
        Workbook workbook = workbookRepository.findById(workbookId)
                .orElseThrow(()-> new ResourceNotFoundException("Workbook","id", workbookId));
        Question question = questionRepository.findById(questionId)
                .orElseThrow(()-> new ResourceNotFoundException("Question","id", questionId));
        if(workbook.getQuestionIds().stream().noneMatch(q -> q.equals(question.getId()))){
            throw new WorkbookException(HttpStatus.BAD_REQUEST, "세트에 해당 문제가 존재하지 않습니다.");
        }
        if (!workbook.getTeacherId().equals(teacherId))
            throw new NotAuthorizedException("작성자만 삭제할 수 있습니다.");
        workbook.getQuestionIds().remove(question.getId());
        workbookRepository.save(workbook);
        workbookService.updateWorkbookDifficulty(workbook.getId());
    }

    @Override
    public void addAlreadyExistingQuestion(String teacherId, String workbookId, String questionId) {
        Workbook workbook = workbookRepository.findById(workbookId)
                .orElseThrow(()-> new ResourceNotFoundException("Workbook","id", workbookId));
        Question question = questionRepository.findById(questionId)
                .orElseThrow(()-> new ResourceNotFoundException("Question","id", questionId));
        if(workbook.getQuestionIds().contains(questionId)){
            throw new WorkbookException(HttpStatus.BAD_REQUEST, "세트에 이미 해당 문제가 존재합니다.");
        }
        if (!workbook.getTeacherId().equals(teacherId))
            throw new NotAuthorizedException("작성자만 수정할 수 있습니다.");
        workbook.getQuestionIds().add(question.getId());
        workbookRepository.save(workbook);
        workbookService.updateWorkbookDifficulty(workbook.getId());
    }

    @Override
    public QuestionAudioPathDto getQuestionAudioPath(String questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(()-> new ResourceNotFoundException("Question","id", questionId));
        Optional<String> audioPath = Optional.ofNullable(question.getAudioFilePath());
        if(audioPath.isEmpty()) throw new ResourceNotFoundException("Question", "audioFilePath", questionId);
        return new QuestionAudioPathDto(audioPath.get());
    }
}
