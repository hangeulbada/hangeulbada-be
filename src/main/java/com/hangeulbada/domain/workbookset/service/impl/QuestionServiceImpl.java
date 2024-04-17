package com.hangeulbada.domain.workbookset.service.impl;

import com.hangeulbada.domain.workbookset.entity.Question;
import com.hangeulbada.domain.workbookset.entity.Workbook;
import com.hangeulbada.domain.workbookset.exception.ResourceNotFoundException;
import com.hangeulbada.domain.workbookset.exception.WorkbookException;
import com.hangeulbada.domain.workbookset.dto.QuestionDto;
import com.hangeulbada.domain.workbookset.repository.QuestionRepository;
import com.hangeulbada.domain.workbookset.repository.WorkbookRepository;
import com.hangeulbada.domain.workbookset.service.QuestionSerivce;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionSerivce {
    private final QuestionRepository questionRepository;
    private final WorkbookRepository workbookRepository;
    private final ModelMapper mapper;

    @Override
    public List<QuestionDto> getQuestionsByWorkbookId(String workbookId) {
        Workbook workbook = workbookRepository.findById(workbookId)
                .orElseThrow(()-> new ResourceNotFoundException("Workbook","id", workbookId));
        List<Question> questionList = questionRepository.findByWorkbookId(workbookId);
        return questionList.stream().map(question -> mapper.map(question, QuestionDto.class)).collect(Collectors.toList());

    }

    @Override
    public QuestionDto createQuestion(String workbookId, QuestionDto questionDto) {
        Question question = mapper.map(questionDto, Question.class);

        Workbook workbook = workbookRepository.findById(workbookId)
                .orElseThrow(()-> new ResourceNotFoundException("Workbook","id", workbookId));
        question.setWorkbook(workbook);
        Question newQuestion = questionRepository.save(question);

        return mapper.map(newQuestion, QuestionDto.class);
    }

    @Override
    public QuestionDto getQuestionById(String workbookId, String questionId) {
        Workbook workbook = workbookRepository.findById(workbookId)
                .orElseThrow(()-> new ResourceNotFoundException("Workbook","id", workbookId));
        Question question = questionRepository.findById(questionId)
                .orElseThrow(()-> new ResourceNotFoundException("Question","id", questionId));
        if(!question.getWorkbook().getId().equals(workbook.getId())){
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
        if(!question.getWorkbook().getId().equals(workbook.getId())){
            throw new WorkbookException(HttpStatus.BAD_REQUEST, "세트에 해당 문제가 존재하지 않습니다.");
        }
        questionRepository.delete(question);
    }
}
