package com.hangeulbada.domain.workbookset.service.impl;

import com.hangeulbada.domain.workbookset.dto.QuestionDto;
import com.hangeulbada.domain.workbookset.dto.QuestionRequestDto;
import com.hangeulbada.domain.workbookset.entity.Question;
import com.hangeulbada.domain.workbookset.entity.Workbook;
import com.hangeulbada.domain.workbookset.exception.ResourceNotFoundException;
import com.hangeulbada.domain.workbookset.exception.WorkbookException;
import com.hangeulbada.domain.workbookset.repository.QuestionRepository;
import com.hangeulbada.domain.workbookset.repository.WorkbookRepository;
import com.hangeulbada.domain.workbookset.service.QuestionSerivce;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionSerivce {
    private final QuestionRepository questionRepository;
    private final WorkbookRepository workbookRepository;
    private final ModelMapper mapper;

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
    public QuestionDto createQuestion(String workbookId, QuestionRequestDto questionRequestDto) {
        Workbook w = workbookRepository.findById(workbookId)
                .orElseThrow(()-> new ResourceNotFoundException("Workbook","id", workbookId));

        QuestionDto questionDto = QuestionDto.builder()
                .content(questionRequestDto.getContent())
                .teacherId(questionRequestDto.getTeacherId())
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
    public void deleteQuestion(String workbookId, String questionId) {
        Workbook workbook = workbookRepository.findById(workbookId)
                .orElseThrow(()-> new ResourceNotFoundException("Workbook","id", workbookId));
        Question question = questionRepository.findById(questionId)
                .orElseThrow(()-> new ResourceNotFoundException("Question","id", questionId));
        if(workbook.getQuestionIds().stream().noneMatch(q -> q.equals(question.getId()))){
            throw new WorkbookException(HttpStatus.BAD_REQUEST, "세트에 해당 문제가 존재하지 않습니다.");
        }
        workbook.getQuestionIds().remove(question.getId());
        workbookRepository.save(workbook);
        questionRepository.delete(question);
    }
}
