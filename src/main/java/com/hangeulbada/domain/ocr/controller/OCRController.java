package com.hangeulbada.domain.ocr.controller;

import com.hangeulbada.domain.ocr.dto.OCRRequest;
import com.hangeulbada.domain.ocr.service.OCRService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ocr")
@RequiredArgsConstructor
public class OCRController {
    private final OCRService ocrService;

    @PostMapping("/submit")
    public ResponseEntity<?> submit(@RequestBody OCRRequest ocrRequest) {
        String ans = ocrService.start();
        return ResponseEntity.ok(ans);
    }
}
