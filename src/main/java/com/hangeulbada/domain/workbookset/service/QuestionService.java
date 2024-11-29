package com.hangeulbada.domain.workbookset.service;

import com.hangeulbada.domain.workbookset.dto.*;

import java.util.List;

public interface QuestionService {
    List<QuestionResponseDto> getQuestionsByWorkbookId(String workbookId);
    String createAiGeneratedQuestions(ClaudeRequestDto claudeRequestDto);
    void deleteQuestion(String teacherId, String questionId);
    List<QuestionResponseDto> getAllQuestions(String teacherId);
    QuestionResponseDto createQuestion(String teacherId, String workbookId, QuestionContentResponseDto questionsDto);
    WorkbookDto getQuestionsToCreate(String teacherId, String workbookId, List<QuestionContentResponseDto> questions);
    WorkbookDto getAiQuestionsToCreate(String teacherId, String workbookId, AiGeneratedQuestionsDto questionsDto);
    WorkbookDto getAlreadyExistingQuestionToAdd(String teacherId, String workbookId, QuestionRequestListDto questionIds);
    QuestionResponseDto getQuestionById(String workbookId, String questionId);
    void deleteQuestionFromWorkbook(String teacherId, String workbookId, String questionId);
    void addAlreadyExistingQuestion(String teacherId, String workbookId, String questionId);
    QuestionAudioPathDto getQuestionAudioPath(String questionId);
}

