package com.hangeulbada.domain.workbookset.service;

import com.hangeulbada.domain.workbookset.dto.QuestionDto;

import java.util.List;

public interface QuestionSerivce {
    List<QuestionDto> getQuestionsByWorkbookId(String workbookId);
    QuestionDto createQuestion(String workbookId, QuestionDto questionDto);
    QuestionDto getQuestionById(String workbookId, String questionId);
    void deleteQuestion(String workbookId, String questionId);
}
