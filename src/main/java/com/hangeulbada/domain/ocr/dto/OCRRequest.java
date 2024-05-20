package com.hangeulbada.domain.ocr.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "OCR 요청")
public class OCRRequest {
    @Schema(description = "OCR 이미지 파일명", example = "{studentId}_{workbookId}")
    private String imageName;
}
