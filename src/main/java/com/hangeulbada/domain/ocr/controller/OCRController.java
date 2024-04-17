package com.hangeulbada.domain.ocr.controller;

import com.hangeulbada.domain.ocr.dto.OCRRequest;
import com.hangeulbada.domain.ocr.dto.ScoreDTO;
import com.hangeulbada.domain.ocr.service.OCRService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ocr")
@RequiredArgsConstructor
public class OCRController {
    private final OCRService ocrService;

    @PostMapping("/submit")
    @Operation(summary="OCR 요청", description="OCR 요청 전송")
    @ApiResponse(responseCode = "200", description = "OCR 후 문항별 점수 반환")
    public ResponseEntity<List<ScoreDTO>> submit(
            @RequestBody @Parameter(description = "OCR 요청 데이터", required = true, content = @Content(schema = @Schema(implementation = OCRRequest.class)))
            OCRRequest ocrRequest) {
        String image = ocrRequest.getOcrImage();
        System.out.println(image);
        ocrService.start();
//        String s3Url = ocrRequest.getOcrImage();

        List<ScoreDTO> scoreList = null;

        return ResponseEntity.ok(scoreList);
    }
}
