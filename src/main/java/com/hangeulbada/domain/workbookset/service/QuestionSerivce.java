package com.hangeulbada.domain.workbookset.service;

import com.hangeulbada.domain.workbookset.dto.QuestionDto;
import com.hangeulbada.domain.workbookset.dto.QuestionsDto;

import java.util.List;

public interface QuestionSerivce {
    List<QuestionDto> getQuestionsByWorkbookId(String workbookId);
    List<QuestionDto> createQuestion(String workbookId, QuestionsDto questionsDto);
    QuestionDto getQuestionById(String workbookId, String questionId);
    void deleteQuestion(String workbookId, String questionId);
}
