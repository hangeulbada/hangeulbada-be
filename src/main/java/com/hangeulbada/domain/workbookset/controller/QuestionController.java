package com.hangeulbada.domain.workbookset.controller;

import com.hangeulbada.domain.workbookset.dto.*;
import com.hangeulbada.domain.workbookset.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Question", description = "문제 관리 API")
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping("/questions")
    @Operation(summary = "전체 문제 조회", description = "유저가 생성한 모든 문제를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    public ResponseEntity<List<QuestionResponseDto>> getAllQuestions(Principal principal){
        return ResponseEntity.ok(questionService.getAllQuestions(principal.getName()));
    }

    @PostMapping("/ai-generate")
    @Operation(summary = "AI 문제 생성 세트", description = "AI로 문제를 생성합니다.")
    @ApiResponse(responseCode = "201", description = "생성 성공")
    public ResponseEntity<String> createAiGeneratedWorkbook(@Valid @RequestBody ClaudeRequestDto claudeRequestDto){
        return new ResponseEntity<>(questionService.createAiGeneratedQuestions(claudeRequestDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/questions/{questionId}")
    @Operation(summary = "문제 삭제", description = "문제를 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "삭제 성공")
    @ApiResponse(responseCode = "404", description = "존재하지 않는 객체입니다. (Exception 발생)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "401", description = "작성자만 수정할 수 있습니다. (Exception 발생)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "500", description = "S3 오류 (Exception 발생)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    public ResponseEntity<String> deleteQuestion(@PathVariable(name="questionId")String questionId,
                                                 Principal principal){
        questionService.deleteQuestion(principal.getName(),questionId);
        return new ResponseEntity<>("성공적으로 문제가 삭제되었습니다.", HttpStatus.OK);
    }



    @PostMapping("/workbook/{workbookId}/questions/new")
    @Operation(summary = "문제 생성", description = "세트 내에 문제를 생성합니다.")
    @ApiResponse(responseCode = "201", description = "생성 성공")
    @ApiResponse(responseCode = "404", description = "존재하지 않는 객체입니다. (Exception 발생)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "401", description = "작성자만 수정할 수 있습니다. (Exception 발생)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "500", description = "S3 오류 (Exception 발생)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "502", description = "TTS API 오류 (Exception 발생)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    public ResponseEntity<WorkbookDto> getQuestionsToCreate(@PathVariable(name = "workbookId") String workbookId,
                                                            @Valid @RequestBody List<QuestionContentResponseDto> questions,
                                                            Principal principal){
        return new ResponseEntity<>(questionService.getQuestionsToCreate(principal.getName(), workbookId, questions), HttpStatus.CREATED);
    }

    @PostMapping("/workbook/{workbookId}/questions/new-ai")
    @Operation(summary = "AI로 생성된 문제로 문제집 구성", description = "세트 내에 AI로 생성된 문제를 생성합니다.")
    @ApiResponse(responseCode = "201", description = "생성 성공")
    @ApiResponse(responseCode = "404", description = "존재하지 않는 객체입니다. (Exception 발생)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "401", description = "작성자만 수정할 수 있습니다. (Exception 발생)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "500", description = "S3 오류 (Exception 발생)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "502", description = "TTS API 오류 (Exception 발생)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    public ResponseEntity<WorkbookDto> getAIQuestionsToCreate(@PathVariable(name = "workbookId") String workbookId,
                                                            @Valid @RequestBody AiGeneratedQuestionsDto questionsDto,
                                                            Principal principal){
        return new ResponseEntity<>(questionService.getAiQuestionsToCreate(principal.getName(), workbookId, questionsDto), HttpStatus.CREATED);
    }

    @GetMapping("/workbook/{workbookId}/questions")
    @Operation(summary = "세트 내 문제 조회", description = "세트 내의 모든 문제를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @ApiResponse(responseCode = "404", description = "존재하지 않는 객체입니다. (Exception 발생)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    public ResponseEntity<List<QuestionResponseDto>> getQuestionsByWorkbookId(@PathVariable(name="workbookId")String id){
        return ResponseEntity.ok(questionService.getQuestionsByWorkbookId(id));
    }

    @GetMapping("/workbook/{workbookId}/questions/{questionId}")
    @Operation(summary = "문제 조회", description = "세트 내의 문제를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @ApiResponse(responseCode = "404", description = "존재하지 않는 객체입니다. (Exception 발생)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    public ResponseEntity<QuestionResponseDto> getQuestionById(@PathVariable(name="workbookId")String workbookId,
                                                       @PathVariable(name="questionId")String questionId){
        return new ResponseEntity<>(questionService.getQuestionById(workbookId,questionId), HttpStatus.OK);
    }



    @PostMapping("/workbook/{workbookId}/questions/existing")
    @Operation(summary = "기존 문제로 세트 구성", description = "이미 작성되어 있던 문제들을 세트에 추가합니다.")
    @ApiResponse(responseCode = "201", description = "생성 성공")
    @ApiResponse(responseCode = "404", description = "존재하지 않는 객체입니다. (Exception 발생)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "401", description = "작성자만 수정할 수 있습니다. (Exception 발생)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    public ResponseEntity<String> getAlreadyExistingQuestionToAdd(@PathVariable(name="workbookId")String workbookId,
                                                                  @Valid @RequestBody QuestionRequestListDto questionIds,
                                                                       Principal principal){
        questionService.getAlreadyExistingQuestionToAdd(principal.getName(), workbookId, questionIds);
        return new ResponseEntity<>("문제가 성공적으로 추가되었습니다.", HttpStatus.CREATED);
    }

    @PostMapping("/workbook/{workbookId}/questions")
    @Operation(summary = "새 문제를 기존 세트에 추가", description = "기존 세트 내에 문제를 추가합니다 (한 문장 추가).")
    @ApiResponse(responseCode = "201", description = "생성 성공")
    @ApiResponse(responseCode = "404", description = "존재하지 않는 객체입니다. (Exception 발생)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "401", description = "작성자만 수정할 수 있습니다. (Exception 발생)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "500", description = "S3 오류 (Exception 발생)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "502", description = "TTS API 오류 (Exception 발생)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    public ResponseEntity<QuestionResponseDto> createQuestion(@PathVariable(name = "workbookId") String workbookId,
                                                      @Valid @RequestBody QuestionContentResponseDto questionDto,
                                                      Principal principal){
        return new ResponseEntity<>(questionService.createQuestion(principal.getName(), workbookId, questionDto), HttpStatus.CREATED);
    }

    @PostMapping("/workbook/{workbookId}/questions/{questionId}")
    @Operation(summary = "기존 문제 기존 세트에 추가", description = "이미 작성되어 있던 문제를 세트에 추가합니다.(한 문제 추가)")
    @ApiResponse(responseCode = "201", description = "조회 성공")
    @ApiResponse(responseCode = "404", description = "존재하지 않는 객체입니다. (Exception 발생)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "401", description = "작성자만 수정할 수 있습니다. (Exception 발생)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    public ResponseEntity<String> addAlreadyExistingQuestionToWorkbook(@PathVariable(name="workbookId")String workbookId,
                                                                       @PathVariable(name="questionId")String questionId,
                                                                       Principal principal){
        questionService.addAlreadyExistingQuestion(principal.getName(), workbookId, questionId);
        return new ResponseEntity<>("문제가 성공적으로 추가되었습니다.", HttpStatus.CREATED);
    }

    @DeleteMapping("/workbook/{workbookId}/questions/{questionId}")
    @Operation(summary = "세트에서 문제 제거", description = "세트의 문제 리스트에서 문제를 제거합니다.")
    @ApiResponse(responseCode = "200", description = "삭제 성공")
    @ApiResponse(responseCode = "404", description = "존재하지 않는 객체입니다. (Exception 발생)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "401", description = "작성자만 수정할 수 있습니다. (Exception 발생)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    public ResponseEntity<String> deleteQuestion(@PathVariable(name="workbookId")String workbookId,
                                                       @PathVariable(name="questionId")String questionId,
                                                 Principal principal){
        questionService.deleteQuestionFromWorkbook(principal.getName(), workbookId,questionId);
        return new ResponseEntity<>("세트에서 문제를 제거하였습니다.", HttpStatus.OK);
    }

    @GetMapping("/questions/{questionId}/audio")
    @Operation(summary = "문제 음성 파일 경로 조회", description = "해당 문제의 음성 파일 경로를 반환합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @ApiResponse(responseCode = "404", description = "존재하지 않는 객체입니다. (Exception 발생)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "502", description = "TTS API 오류 (Exception 발생)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    public ResponseEntity<QuestionAudioPathDto> getAudioFilePath(@PathVariable(name="questionId") String questionId){
        return ResponseEntity.ok(questionService.getQuestionAudioPath(questionId));
    }

}
