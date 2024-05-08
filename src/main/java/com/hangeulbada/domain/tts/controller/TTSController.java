package com.hangeulbada.domain.tts.controller;

import com.hangeulbada.domain.tts.service.TTSService;
import com.hangeulbada.domain.workbookset.dto.QuestionDto;
import com.hangeulbada.domain.workbookset.service.QuestionSerivce;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tts")
@RequiredArgsConstructor
public class TTSController {
    private final TTSService ttsService;
    private final QuestionSerivce questionService;

    @PostMapping("/tts")
    public ResponseEntity<?> submit2() {
        String ans = ttsService.tts();
        return ResponseEntity.ok(ans);
    }
    @GetMapping("/tts/{questionId}")
    public ResponseEntity<QuestionDto> getQuestionById(@PathVariable(name="workbookId")String workbookId,
                                                       @PathVariable(name="questionId")String questionId){
        String audioFilePath = ttsService.tts(); // tts() 메서드로 음성 파일 생성
        if(StringUtils.isEmpty(audioFilePath)) {
            // 파일 경로가 비어 있으면 오류 처리
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // 파일 경로가 존재하면 해당 파일을 QuestionDto에 설정하여 반환
        QuestionDto questionDto = questionService.getQuestionById(workbookId, questionId);
        questionDto.setAudioFilePath(audioFilePath);
        return new ResponseEntity<>(questionDto, HttpStatus.OK);
    }
}
