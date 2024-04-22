package com.hangeulbada.domain.workbookset.service;

import com.hangeulbada.domain.workbookset.dto.QuestionDto;
import com.hangeulbada.domain.workbookset.dto.QuestionRequestDto;

import java.util.List;

public interface QuestionSerivce {
    List<QuestionDto> getQuestionsByWorkbookId(String workbookId);
    QuestionDto createQuestion(String workbookId, QuestionRequestDto questionsDto);
    QuestionDto getQuestionById(String workbookId, String questionId);
    void deleteQuestion(String workbookId, String questionId);
}
