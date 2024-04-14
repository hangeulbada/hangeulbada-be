package com.hangeulbada.domain.ocr.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "OCR 요청")
public class OCRRequest {
    @Schema(description = "학생 uid", example = "598237442")
    private String studentUid;
    @Schema(description = "OCR 이미지 S3 주소", example = "/ocr_image.jpg")
    private String ocrImage;
}
