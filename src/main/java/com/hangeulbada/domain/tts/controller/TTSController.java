package com.hangeulbada.domain.tts.controller;

import com.hangeulbada.domain.tts.service.TTSService;
import com.hangeulbada.domain.workbookset.dto.QuestionDto;
import com.hangeulbada.domain.workbookset.service.QuestionSerivce;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tts")
@RequiredArgsConstructor
public class TTSController {
    private final TTSService ttsService;
    private final QuestionSerivce questionService;
    @PostMapping("/tts")
    public ResponseEntity<?> submit2(@RequestBody QuestionDto questionDto) {
        QuestionDto savedQuestion = questionService.saveQuestion(questionDto);
        String audioFilePath = ttsService.tts(savedQuestion.getContent());
        if(StringUtils.isEmpty(audioFilePath)){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        savedQuestion.setAudioFilePath(audioFilePath);
        questionService.updateQuestion(savedQuestion);
        return ResponseEntity.ok(savedQuestion);
    }
    @GetMapping("/tts/{questionId}")
    public ResponseEntity<QuestionDto> getQuestionById(@PathVariable(name="workbookId")String workbookId,
                                                       @PathVariable(name="questionId")String questionId){
        QuestionDto questionDto = questionService.getQuestionById(workbookId, questionId);
        String audioFilePath = ttsService.tts(questionDto.getContent());
        if(StringUtils.isEmpty(audioFilePath)) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        questionDto.setAudioFilePath(audioFilePath);
        return new ResponseEntity<>(questionDto, HttpStatus.OK);
    }
}
