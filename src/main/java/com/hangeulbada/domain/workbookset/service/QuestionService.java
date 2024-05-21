package com.hangeulbada.domain.workbookset.service;

import com.hangeulbada.domain.workbookset.dto.QuestionDto;
import com.hangeulbada.domain.workbookset.dto.QuestionRequestDto;
import com.hangeulbada.domain.workbookset.dto.QuestionRequestListDto;
import com.hangeulbada.domain.workbookset.dto.WorkbookDto;

import java.util.List;

public interface QuestionService {
    List<QuestionDto> getQuestionsByWorkbookId(String workbookId);
    void deleteQuestion(String teacherId, String questionId);
    List<QuestionDto> getAllQuestions(String teacherId);
    QuestionDto createQuestion(String teacherId, String workbookId, QuestionRequestDto questionsDto);
    WorkbookDto getQuestionsToCreate(String teacherId, String workbookId, QuestionRequestListDto questions);
    WorkbookDto getAlreadyExistingQuestionToAdd(String teacherId, String workbookId, QuestionRequestListDto questionIds);
    QuestionDto getQuestionById(String workbookId, String questionId);
    void deleteQuestionFromWorkbook(String teacherId, String workbookId, String questionId);
    void addAlreadyExistingQuestion(String teacherId, String workbookId, String questionId);
    QuestionDto saveQuestion(QuestionDto questionDto);
    void updateQuestion(QuestionDto questionDto);
}

