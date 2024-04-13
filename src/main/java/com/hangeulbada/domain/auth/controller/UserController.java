package com.hangeulbada.domain.auth.controller;

import com.hangeulbada.domain.auth.dto.LoginResponse;
import com.hangeulbada.domain.auth.dto.SignupResponse;
import com.hangeulbada.domain.auth.service.GoogleService;
import com.hangeulbada.domain.auth.service.KakaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
//    @ResponseBody
//    @GetMapping("/oauth2/kakao")
//    @ApiOperation(value = "웹 카카오 로그인", notes = "웹 프론트 버전 카카오 로그인")
//    public ResponseEntity<LoginResponse> kakaoLogin(@RequestParam String code, HttpServletRequest request) {
//        try {
//            // 현재 도메인 확인
//            String currentDomain = request.getServerName();
//            return ResponseEntity.ok(kakaoService.kakaoLogin(code, currentDomain));
//        } catch (NoSuchElementException e) {
//
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item Not Found");
//        }
//    }
    @ResponseBody
    @GetMapping("/login/oauth2/code/google")
    @ApiOperation(value = "웹 구글 로그인", notes = "웹 프론트 버전 구글 로그인")
    public ResponseEntity<LoginResponse> googleRedirectUri(@RequestParam String code) {
        try {
            log.info("code: "+code);
            LoginResponse response = googleService.googleOauth2(code);
            if (response.getToken()!=null){
                return ResponseEntity.ok(response);
            }
            else{
                // 로그인 실패시 response 보내면서 실패 메시지 전달
                response.setMessage("로그인 실패");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } catch (NoSuchElementException e) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item Not Found");
        }
    }

    @ResponseBody
    @ApiOperation(value = "웹 구글 회원 가입", notes = "웹 프론트 버전 구글 회원 가입")
    @PostMapping("/login/oauth2/code/google/signup")
    public ResponseEntity<LoginResponse> googleSignup(SignupResponse signupResponse) {
        try {
            log.info("LoginResponse: "+signupResponse);
            return ResponseEntity.ok(googleService.googleSignup(signupResponse));
        } catch (NoSuchElementException e) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item Not Found");
        }
    }
}