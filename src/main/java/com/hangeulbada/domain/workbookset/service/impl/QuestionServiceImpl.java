package com.hangeulbada.domain.workbookset.service.impl;

import com.hangeulbada.domain.auth.repository.UserRepository;
import com.hangeulbada.domain.workbookset.dto.QuestionDto;
import com.hangeulbada.domain.workbookset.dto.QuestionsDto;
import com.hangeulbada.domain.workbookset.entity.Question;
import com.hangeulbada.domain.workbookset.entity.Workbook;
import com.hangeulbada.domain.workbookset.exception.ResourceNotFoundException;
import com.hangeulbada.domain.workbookset.exception.WorkbookException;
import com.hangeulbada.domain.workbookset.repository.QuestionRepository;
import com.hangeulbada.domain.workbookset.repository.WorkbookRepository;
import com.hangeulbada.domain.workbookset.service.QuestionSerivce;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionSerivce {
    private final QuestionRepository questionRepository;
    private final WorkbookRepository workbookRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Override
    public List<QuestionDto> getQuestionsByWorkbookId(String workbookId) {
        Workbook workbook = workbookRepository.findById(workbookId)
                .orElseThrow(()-> new ResourceNotFoundException("Workbook","id", workbookId));

        List<String> questionIds = workbook.getQuestions();
        List<QuestionDto> questionList = new ArrayList<>();
        for (String q: questionIds) {
            Question question = questionRepository.findById(q)
                    .orElseThrow(()-> new ResourceNotFoundException("Question","id", q));
            questionList.add(mapper.map(question, QuestionDto.class));
        }

        return questionList;
    }

    @Override
    public List<QuestionDto> createQuestion(String workbookId, QuestionsDto questionsDto) {
        Workbook w = workbookRepository.findById(workbookId)
                .orElseThrow(()-> new ResourceNotFoundException("Workbook","id", workbookId));
        String teacherId = questionsDto.getTeacherId();

        List<String> content = questionsDto.getContent();

        for (String c: content) {
            QuestionDto question = QuestionDto.builder()
                    .teacherId(teacherId)
                    .content(c)
                    .build();
            Question newQuestion = questionRepository.save(mapper.map(question, Question.class));
            w.getQuestions().add(newQuestion.getId());
        }
        return getQuestionsByWorkbookId(workbookId);
    }


    @Override
    public QuestionDto getQuestionById(String workbookId, String questionId) {
        Workbook workbook = workbookRepository.findById(workbookId)
                .orElseThrow(()-> new ResourceNotFoundException("Workbook","id", workbookId));

        Question question = questionRepository.findById(questionId)
                .orElseThrow(()-> new ResourceNotFoundException("Question","id", questionId));

        if(workbook.getQuestions().stream().noneMatch(q -> q.equals(question.getId()))){
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
        if(workbook.getQuestions().stream().noneMatch(q -> q.equals(question.getId()))){
            throw new WorkbookException(HttpStatus.BAD_REQUEST, "세트에 해당 문제가 존재하지 않습니다.");
        }
        workbook.getQuestions().remove(question.getId());
        workbookRepository.save(workbook);
        questionRepository.delete(question);
    }
}
