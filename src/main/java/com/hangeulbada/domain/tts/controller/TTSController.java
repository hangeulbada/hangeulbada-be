package com.hangeulbada.domain.tts.controller;

import com.hangeulbada.domain.tts.service.TTSService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tts")
@RequiredArgsConstructor
public class TTSController {
    private final TTSService ttsService;

    @PostMapping("/tts")
    public ResponseEntity<?> submit2() {
        String ans = ttsService.tts();

        return ResponseEntity.ok(ans);
    }
}
