package com.hangeulbada.domain.workbookset.service.impl;

import com.hangeulbada.domain.workbookset.dto.QuestionDto;
import com.hangeulbada.domain.workbookset.dto.QuestionRequestDto;
import com.hangeulbada.domain.workbookset.dto.QuestionRequestListDto;
import com.hangeulbada.domain.workbookset.dto.WorkbookDto;
import com.hangeulbada.domain.workbookset.entity.Question;
import com.hangeulbada.domain.workbookset.entity.Workbook;
import com.hangeulbada.domain.workbookset.exception.NotAuthorizedException;
import com.hangeulbada.domain.workbookset.exception.ResourceNotFoundException;
import com.hangeulbada.domain.workbookset.exception.WorkbookException;
import com.hangeulbada.domain.workbookset.repository.QuestionRepository;
import com.hangeulbada.domain.workbookset.repository.WorkbookRepository;
import com.hangeulbada.domain.workbookset.service.QuestionService;
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
    private final ModelMapper modelMapper;

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
    public void deleteQuestion(String teacherId, String questionId) {
        Question question= questionRepository.findById(questionId)
                .orElseThrow(()-> new ResourceNotFoundException("Question","id", questionId));
        if (!question.getTeacherId().equals(teacherId))
            throw new NotAuthorizedException("작성자만 삭제할 수 있습니다.");

        //모든 workbook에서 해당 question 지움
        for(Workbook w : workbookRepository.findAll()){
            if(w.getQuestionIds().contains(questionId)) {
                deleteQuestionFromWorkbook(w.getTeacherId(), w.getId(), questionId);
            }
        }
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

        QuestionDto questionDto = QuestionDto.builder()
                .content(questionRequestDto.getContent())
                .teacherId(teacherId)
                .build();
        Question newQuestion = questionRepository.save(mapper.map(questionDto, Question.class));

        if(w.getQuestionIds()==null){
            w.setQuestionIds(new ArrayList<>());
        }
        w.getQuestionIds().add(newQuestion.getId());
        workbookRepository.save(w);
        return mapper.map(newQuestion, QuestionDto.class);
    }

    @Override
    public WorkbookDto getQuestionsToCreate(String teacherId, String workbookId, QuestionRequestListDto questions) {
        Workbook w = workbookRepository.findById(workbookId)
                .orElseThrow(()-> new ResourceNotFoundException("Workbook","id", workbookId));
        List<String> questionIds = new ArrayList<>();
        for(String q : questions.getContent()){
            QuestionDto questionDto = QuestionDto.builder().teacherId(teacherId).content(q).build();
            Question newQuestion = questionRepository.save(mapper.map(questionDto, Question.class));
            questionIds.add(newQuestion.getId());
        }
        w.setQuestionIds(questionIds);
        workbookRepository.save(w);
        return mapper.map(w, WorkbookDto.class);
    }

    @Override
    public WorkbookDto getAlreadyExistingQuestionToAdd(String teacherId, String workbookId, QuestionRequestListDto questionIds) {
        Workbook w = workbookRepository.findById(workbookId)
                .orElseThrow(()-> new ResourceNotFoundException("Workbook","id", workbookId));
        List<String> qIds = new ArrayList<>();
        for(String qId : questionIds.getContent()){
            Optional<Question> question = questionRepository.findById(qId);
            if (!question.isEmpty()) qIds.add(qId);
        }
        w.setQuestionIds(qIds);
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
//        questionRepository.delete(question);
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
    }
    @Override
    public QuestionDto saveQuestion(QuestionDto questionDto) {
        Question question = convertToEntity(questionDto);
        Question savedQuestion = questionRepository.save(question);
        return convertToDto(savedQuestion);
    }
    private Question convertToEntity(QuestionDto questionDto) {
        return Question.builder()
                .id(questionDto.getId())
                .teacherId(questionDto.getTeacherId())
                .content(questionDto.getContent())
                .audioFilePath(questionDto.getAudioFilePath())
                .build();
    }
    @Override
    public void updateQuestion(QuestionDto questionDto) {
        Question question = convertToEntity(questionDto);
        questionRepository.save(question);
    }
    private QuestionDto convertToDto(Question question) {
        return QuestionDto.builder()
                .id(question.getId())
                .teacherId(question.getTeacherId())
                .content(question.getContent())
                .audioFilePath(question.getAudioFilePath())
                .build();
    }
}
