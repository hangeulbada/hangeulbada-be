package com.hangeulbada.domain.workbookset.controller;

import com.hangeulbada.domain.workbookset.dto.QuestionDto;
import com.hangeulbada.domain.workbookset.service.QuestionSerivce;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workbook/{workbookId}/questions")
public class QuestionController {
    private QuestionSerivce questionSerivce;

    public QuestionController(QuestionSerivce questionSerivce) {
        this.questionSerivce = questionSerivce;
    }

    @PostMapping
    public ResponseEntity<QuestionDto> createQuestion(@PathVariable(name = "workbookId") long workbookId,
                                                      @Valid @RequestBody QuestionDto questionDto){
        return new ResponseEntity<>(questionSerivce.createQuestion(workbookId, questionDto), HttpStatus.CREATED);
    }

    @GetMapping
    public List<QuestionDto> getQuestionsByWorkbookId(@PathVariable(name="workbookId")long id){
        return questionSerivce.getQuestionsByWorkbookId(id);
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionDto> getQuestionById(@PathVariable(name="workbookId")long workbookId,
                                                       @PathVariable(name="questionId")long questionId){
        return new ResponseEntity<>(questionSerivce.getQuestionById(workbookId,questionId), HttpStatus.OK);
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<String> deleteQuestion(@PathVariable(name="workbookId")long workbookId,
                                                       @PathVariable(name="questionId")long questionId){
        questionSerivce.deleteQuestion(workbookId,questionId);
        return new ResponseEntity<>("성공적으로 문제가 삭제되었습니다.", HttpStatus.OK);
    }
}
