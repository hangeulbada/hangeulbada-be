package com.hangeulbada.domain.ocr.dto;

import lombok.Data;

@Data
public class OCRRequest {
    private int studentId;
    private String ocrImage;
}
