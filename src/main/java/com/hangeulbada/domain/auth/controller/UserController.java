package com.hangeulbada.domain.auth.controller;

import com.hangeulbada.domain.auth.dto.LoginResponse;
import com.hangeulbada.domain.auth.service.KakaoService;
import com.nimbusds.jose.util.KeyUtils;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Signature;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@Api(tags = {"유저 API"})
public class UserController {

    private final KakaoService kakaoService;

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

    @PostMapping("/token")
    public ResponseEntity<?> token() {
        Key publicKey = KeyUtil.parsingKey* SignatureAlgorithm.forName(getAl)
        return ResponseEntity.ok().build();
    }


}