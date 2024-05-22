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
    @PostMapping("/tts") //문제 저장 후, tts하여 음성파일 생성, 파일의 경로를 문제 데이터에 추가해 저장
    public ResponseEntity<?> saveQuestionWithTTS(@RequestBody QuestionDto questionDto) {
        QuestionDto savedQuestion = questionService.saveQuestion(questionDto);
        //문제 저장 후 tts 호출해 음성파일 생성
        String audioFilePath = ttsService.tts(savedQuestion.getContent());
        if(StringUtils.isEmpty(audioFilePath)){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //tts 파일 경로를 저장된 문제에 설정한다.
        savedQuestion.setAudioFilePath(audioFilePath);
        questionService.updateQuestion(savedQuestion);
        return ResponseEntity.ok(savedQuestion);
    }
    @GetMapping("/tts/{questionId}") //question id에 맞는 문제 데이터 조회해서 반환
    public ResponseEntity<QuestionDto> getQuestionById(@PathVariable(name="workbookId")String workbookId,
                                                       @PathVariable(name="questionId")String questionId){
        QuestionDto questionDto = questionService.getQuestionById(workbookId, questionId);
        if(questionDto ==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(questionDto, HttpStatus.OK);
    }
}
