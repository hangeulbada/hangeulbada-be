package com.hangeulbada.domain.ocr.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "OCR 요청")
public class OCRRequest {
    @Schema(description = "문제집 ID", example = "6453845asd5w2")
    private String workbookId;
    @Schema(description = "OCR 이미지 파일명", example = "unique_image_name.png")
    private String imageName;
}
