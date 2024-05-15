package com.hangeulbada.domain.workbookset.controller;

import com.hangeulbada.domain.workbookset.dto.ExceptionResponse;
import com.hangeulbada.domain.workbookset.dto.QuestionDto;
import com.hangeulbada.domain.workbookset.dto.QuestionRequestDto;
import com.hangeulbada.domain.workbookset.dto.WorkbookDto;
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
@Tag(name = "Question Controller", description = "문제 관리 API")
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping("/questions")
    @Operation(summary = "전체 문제 조회", description = "유저가 생성한 모든 문제를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    public ResponseEntity<List<QuestionDto>> getAllQuestions(Principal principal){
        return ResponseEntity.ok(questionService.getAllQuestions(principal.getName()));
    }

    @DeleteMapping("/questions/{questionId}")
    @Operation(summary = "문제 삭제", description = "문제를 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "삭제 성공")
    @ApiResponse(responseCode = "404", description = "존재하지 않는 객체입니다. (Exception 발생)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "401", description = "작성자만 수정할 수 있습니다. (Exception 발생)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    public ResponseEntity<String> deleteQuestion(@PathVariable(name="questionId")String questionId,
                                                 Principal principal){
        questionService.deleteQuestion(principal.getName(),questionId);
        return new ResponseEntity<>("성공적으로 문제가 삭제되었습니다.", HttpStatus.OK);
    }



    @PostMapping("/workbook/{workbookId}/questions/new")
    @Operation(summary = "문제 생성", description = "세트 내에 문제를 생성합니다.")
    @ApiResponse(responseCode = "200", description = "생성 성공")
    @ApiResponse(responseCode = "404", description = "존재하지 않는 객체입니다. (Exception 발생)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "401", description = "작성자만 수정할 수 있습니다. (Exception 발생)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    public ResponseEntity<WorkbookDto> getQuestionsToCreate(@PathVariable(name = "workbookId") String workbookId,
                                                            @Valid @RequestBody List<QuestionRequestDto> questions,
                                                            Principal principal){
        return new ResponseEntity<>(questionService.getQuestionsToCreate(principal.getName(), workbookId, questions), HttpStatus.CREATED);
    }

    @GetMapping("/workbook/{workbookId}/questions")
    @Operation(summary = "세트 내 문제 조회", description = "세트 내의 모든 문제를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @ApiResponse(responseCode = "404", description = "존재하지 않는 객체입니다. (Exception 발생)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    public ResponseEntity<List<QuestionDto>> getQuestionsByWorkbookId(@PathVariable(name="workbookId")String id){
        return ResponseEntity.ok(questionService.getQuestionsByWorkbookId(id));
    }

    @GetMapping("/workbook/{workbookId}/questions/{questionId}")
    @Operation(summary = "문제 조회", description = "세트 내의 문제를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @ApiResponse(responseCode = "404", description = "존재하지 않는 객체입니다. (Exception 발생)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    public ResponseEntity<QuestionDto> getQuestionById(@PathVariable(name="workbookId")String workbookId,
                                                       @PathVariable(name="questionId")String questionId){
        return new ResponseEntity<>(questionService.getQuestionById(workbookId,questionId), HttpStatus.OK);
    }



    @PostMapping("/workbook/{workbookId}/questions/existing")
    @Operation(summary = "기존 문제로 세트 구성", description = "이미 작성되어 있던 문제들을 세트에 추가합니다.")
    @ApiResponse(responseCode = "200", description = "생성 성공")
    @ApiResponse(responseCode = "404", description = "존재하지 않는 객체입니다. (Exception 발생)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "401", description = "작성자만 수정할 수 있습니다. (Exception 발생)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    public ResponseEntity<String> getAlreadyExistingQuestionToAdd(@PathVariable(name="workbookId")String workbookId,
                                                                  @Valid @RequestBody List<String> questionIds,
                                                                       Principal principal){
        questionService.getAlreadyExistingQuestionToAdd(principal.getName(), workbookId, questionIds);
        return new ResponseEntity<>("문제가 성공적으로 추가되었습니다.", HttpStatus.CREATED);
    }

    @PostMapping("/workbook/{workbookId}/questions")
    @Operation(summary = "새 문제를 기존 세트에 추가", description = "기존 세트 내에 문제를 추가합니다 (한 문장 추가).")
    @ApiResponse(responseCode = "200", description = "생성 성공")
    @ApiResponse(responseCode = "404", description = "존재하지 않는 객체입니다. (Exception 발생)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "401", description = "작성자만 수정할 수 있습니다. (Exception 발생)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    public ResponseEntity<QuestionDto> createQuestion(@PathVariable(name = "workbookId") String workbookId,
                                                      @Valid @RequestBody QuestionRequestDto questionDto,
                                                      Principal principal){
        return new ResponseEntity<>(questionService.createQuestion(principal.getName(), workbookId, questionDto), HttpStatus.CREATED);
    }

    @PostMapping("/workbook/{workbookId}/questions/{questionId}")
    @Operation(summary = "기존 문제 기존 세트에 추가", description = "이미 작성되어 있던 문제를 세트에 추가합니다.(한 문제 추가)")
    @ApiResponse(responseCode = "200", description = "조회 성공")
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

}
