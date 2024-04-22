package com.hangeulbada.domain.workbookset.controller;

import com.hangeulbada.domain.workbookset.dto.QuestionDto;
import com.hangeulbada.domain.workbookset.dto.QuestionRequestDto;
import com.hangeulbada.domain.workbookset.service.QuestionSerivce;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/workbook/{workbookId}/questions")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionSerivce questionSerivce;

    @PostMapping
    public ResponseEntity<QuestionDto> createQuestion(@PathVariable(name = "workbookId") String workbookId,
                                                      @Valid @RequestBody QuestionRequestDto questionsDto){
        return new ResponseEntity<>(questionSerivce.createQuestion(workbookId, questionsDto), HttpStatus.CREATED);
    }

    @GetMapping
    public List<QuestionDto> getQuestionsByWorkbookId(@PathVariable(name="workbookId")String id){
        return questionSerivce.getQuestionsByWorkbookId(id);
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionDto> getQuestionById(@PathVariable(name="workbookId")String workbookId,
                                                       @PathVariable(name="questionId")String questionId){
        return new ResponseEntity<>(questionSerivce.getQuestionById(workbookId,questionId), HttpStatus.OK);
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<String> deleteQuestion(@PathVariable(name="workbookId")String workbookId,
                                                       @PathVariable(name="questionId")String questionId){
        questionSerivce.deleteQuestion(workbookId,questionId);
        return new ResponseEntity<>("성공적으로 문제가 삭제되었습니다.", HttpStatus.OK);
    }
}
