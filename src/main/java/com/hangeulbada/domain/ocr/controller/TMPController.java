package com.hangeulbada.domain.ocr.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TMPController {

    @GetMapping("/")
    public String hello(@AuthenticationPrincipal OAuth2User principal) {
        log.info("Principal에서 꺼낸 OAuth2User = {}", principal);
        // 여기서 principal.getAttribute("name")이 null이 반환되지 않는다고 가정
        return principal.getAttribute("name");
    }
}
