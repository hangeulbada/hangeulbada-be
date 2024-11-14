package com.hangeulbada.domain.workbookset.service;

import com.hangeulbada.domain.workbookset.dto.*;

import java.util.List;

public interface QuestionService {
    List<QuestionDto> getQuestionsByWorkbookId(String workbookId);
    String createAiGeneratedQuestions(ClaudeRequestDto claudeRequestDto);
    void deleteQuestion(String teacherId, String questionId);
    List<QuestionDto> getAllQuestions(String teacherId);
    QuestionDto createQuestion(String teacherId, String workbookId, QuestionRequestDto questionsDto);
    WorkbookDto getQuestionsToCreate(String teacherId, String workbookId, List<QuestionRequestDto> questions);
    WorkbookDto getAlreadyExistingQuestionToAdd(String teacherId, String workbookId, QuestionRequestListDto questionIds);
    QuestionDto getQuestionById(String workbookId, String questionId);
    void deleteQuestionFromWorkbook(String teacherId, String workbookId, String questionId);
    void addAlreadyExistingQuestion(String teacherId, String workbookId, String questionId);
    QuestionAudioPathDto getQuestionAudioPath(String questionId);
}

