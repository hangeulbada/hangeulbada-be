package com.hangeulbada.domain.workbookset.service;

import com.hangeulbada.domain.workbookset.dto.QuestionDto;

import java.util.List;

public interface QuestionSerivce {
    List<QuestionDto> getQuestionsByWorkbookId(long workbookId);
    QuestionDto createQuestion(long workbookId, QuestionDto questionDto);
    QuestionDto getQuestionById(long workbookId, long questionId);
    void deleteQuestion(long workbookId, long questionId);
}
