package com.hangeulbada.domain.auth.controller;

import com.hangeulbada.domain.auth.dto.LoginResponse;
import com.hangeulbada.domain.auth.service.GoogleService;
import com.hangeulbada.domain.auth.service.KakaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = {"유저 API"})
public class UserController {

    private final KakaoService kakaoService;
    private final GoogleService googleService;

    //web 버전
    @ResponseBody
    @GetMapping("/oauth2/kakao")
    @ApiOperation(value = "웹 카카오 로그인", notes = "웹 프론트 버전 카카오 로그인")
    public ResponseEntity<LoginResponse> kakaoLogin(@RequestParam String code, HttpServletRequest request) {
        try {
            // 현재 도메인 확인
            String currentDomain = request.getServerName();
            return ResponseEntity.ok(kakaoService.kakaoLogin(code, currentDomain));
        } catch (NoSuchElementException e) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item Not Found");
        }
    }
    @ResponseBody
    @GetMapping("/login/oauth2/code/google")
    @ApiOperation(value = "웹 구글 로그인", notes = "웹 프론트 버전 구글 로그인")
    public ResponseEntity<LoginResponse> googleLogin(@RequestParam String code, HttpServletRequest request) {
        try {
            // 현재 도메인 확인
            String currentDomain = request.getServerName();
            log.info("currentDomain: "+currentDomain);
            log.info("google code: "+code);

            return ResponseEntity.ok(googleService.googleLogin(code));
        } catch (NoSuchElementException e) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item Not Found");
        }
    }

}