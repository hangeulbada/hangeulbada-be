package com.hangeulbada.domain.ocr.controller;

import com.hangeulbada.domain.ocr.dto.OCRRequest;
import com.hangeulbada.domain.ocr.service.OCRService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    @Operation(summary="OCR 요청", description="OCR 요청 전송")
    public ResponseEntity<?> submit(@Parameter(name = "학생 uid, 이미지 S3주소") @RequestBody OCRRequest ocrRequest) {
        String s3Url = ocrRequest.getOcrImage();
        String ans = ocrService.start();
        return ResponseEntity.ok(ans);
    }
}
