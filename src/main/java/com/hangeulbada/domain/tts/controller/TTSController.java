package com.hangeulbada.domain.tts.controller;

import com.hangeulbada.domain.tts.service.TTSService;
import com.hangeulbada.domain.workbookset.dto.QuestionDto;
import com.hangeulbada.domain.workbookset.service.QuestionService;
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
    private final QuestionService questionService;

    @PostMapping("/tts")
    public ResponseEntity<?> submit2() {
        String ans = ttsService.tts();
        return ResponseEntity.ok(ans);
    }
    @GetMapping("/tts/{questionId}")
    public ResponseEntity<QuestionDto> getQuestionById(@PathVariable(name="workbookId")String workbookId,
                                                       @PathVariable(name="questionId")String questionId){
        String audioFilePath = ttsService.tts();
        if(StringUtils.isEmpty(audioFilePath)) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        QuestionDto questionDto = questionService.getQuestionById(workbookId, questionId);
        questionDto.setAudioFilePath(audioFilePath);
        return new ResponseEntity<>(questionDto, HttpStatus.OK);
    }
}
