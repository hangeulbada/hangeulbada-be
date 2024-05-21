package com.hangeulbada.domain.workbookset.service;

import com.hangeulbada.domain.workbookset.dto.QuestionDto;
import com.hangeulbada.domain.workbookset.dto.QuestionRequestDto;

import java.util.List;

public interface QuestionService {
    List<QuestionDto> getQuestionsByWorkbookId(String workbookId);
    void deleteQuestion(String teacherId, String questionId); //
    List<QuestionDto> getAllQuestions(String teacherId); //
    QuestionDto createQuestion(String teacherId, String workbookId, QuestionRequestDto questionsDto); //
    QuestionDto getQuestionById(String workbookId, String questionId);
    void deleteQuestionFromWorkbook(String teacherId, String workbookId, String questionId); //
    void addAlreadyExistingQuestion(String teacherId, String workbookId, String questionId); //
}
