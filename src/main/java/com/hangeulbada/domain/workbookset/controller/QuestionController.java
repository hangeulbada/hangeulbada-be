package com.hangeulbada.domain.workbookset.controller;

import com.hangeulbada.domain.workbookset.dto.QuestionDto;
import com.hangeulbada.domain.workbookset.dto.QuestionRequestDto;
import com.hangeulbada.domain.workbookset.dto.WorkbookDto;
import com.hangeulbada.domain.workbookset.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
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
    public List<QuestionDto> getAllQuestions(Principal principal){
        return questionService.getAllQuestions(principal.getName());
    }

    @DeleteMapping("/questions/{questionId}")
    @Operation(summary = "문제 삭제", description = "문제를 삭제합니다.")
    public ResponseEntity<String> deleteQuestion(@PathVariable(name="questionId")String questionId,
                                                 Principal principal){
        questionService.deleteQuestion(principal.getName(),questionId);
        return new ResponseEntity<>("성공적으로 문제가 삭제되었습니다.", HttpStatus.OK);
    }

//    @PostMapping("/workbook/{workbookId}/questions")
//    @Operation(summary = "문제 생성", description = "세트 내에 문제를 생성합니다.")
//    public ResponseEntity<QuestionDto> createQuestion(@PathVariable(name = "workbookId") String workbookId,
//                                                      @Valid @RequestBody QuestionRequestDto questionDto,
//                                                      Principal principal){
//        return new ResponseEntity<>(questionService.createQuestion(principal.getName(), workbookId, questionDto), HttpStatus.CREATED);
//    }

    @PostMapping("/workbook/{workbookId}/questions/new")
    @Operation(summary = "문제 생성", description = "세트 내에 문제를 생성합니다.")
    public ResponseEntity<WorkbookDto> getQuestionsToCreate(@PathVariable(name = "workbookId") String workbookId,
                                                            @Valid @RequestBody List<String> questions,
                                                            Principal principal){
        return new ResponseEntity<>(questionService.getQuestionsToCreate(principal.getName(), workbookId, questions), HttpStatus.CREATED);
    }

    @GetMapping("/workbook/{workbookId}/questions")
    @Operation(summary = "세트 내 문제 조회", description = "세트 내의 모든 문제를 조회합니다.")
    public List<QuestionDto> getQuestionsByWorkbookId(@PathVariable(name="workbookId")String id){
        return questionService.getQuestionsByWorkbookId(id);
    }

    @GetMapping("/workbook/{workbookId}/questions/{questionId}")
    @Operation(summary = "문제 조회", description = "세트 내의 문제를 조회합니다.")
    public ResponseEntity<QuestionDto> getQuestionById(@PathVariable(name="workbookId")String workbookId,
                                                       @PathVariable(name="questionId")String questionId){
        return new ResponseEntity<>(questionService.getQuestionById(workbookId,questionId), HttpStatus.OK);
    }

    @PostMapping("/workbook/{workbookId}/questions/existing")
    @Operation(summary = "기존 문제 세트에 추가", description = "이미 작성되어 있던 문제를 세트에 추가합니다.")
    public ResponseEntity<String> getAlreadyExistingQuestionToAdd(@PathVariable(name="workbookId")String workbookId,
                                                                  @Valid @RequestBody List<String> questionIds,
                                                                       Principal principal){
        questionService.getAlreadyExistingQuestionToAdd(principal.getName(), workbookId, questionIds);
        return new ResponseEntity<>("문제가 성공적으로 추가되었습니다.", HttpStatus.CREATED);
    }

    @DeleteMapping("/workbook/{workbookId}/questions/{questionId}")
    @Operation(summary = "세트에서 문제 제거", description = "세트의 문제 리스트에서 문제를 제거합니다.")
    public ResponseEntity<String> deleteQuestion(@PathVariable(name="workbookId")String workbookId,
                                                       @PathVariable(name="questionId")String questionId,
                                                 Principal principal){
        questionService.deleteQuestionFromWorkbook(principal.getName(), workbookId,questionId);
        return new ResponseEntity<>("세트에서 문제를 제거하였습니다.", HttpStatus.OK);
    }

}
