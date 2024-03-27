package com.hangeulbada.domain.ocr.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OCRService {
    public void submit(int studentId, String ocrImage) {
        System.out.println("studentId = " + studentId);
        System.out.println("ocrImage = " + ocrImage);
    }
}
