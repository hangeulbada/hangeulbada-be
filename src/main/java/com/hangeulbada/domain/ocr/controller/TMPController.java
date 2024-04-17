package com.hangeulbada.domain.ocr.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TMPController {
    @GetMapping("/")
    public String root() {
        return "메인페이지 입니다.";
    }
}
