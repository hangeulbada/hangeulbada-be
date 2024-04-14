package com.hangeulbada.domain.ocr.dto;

import lombok.Data;

@Data
public class OCRRequest {
    private Long studentUid;
    private String ocrImage;
}
