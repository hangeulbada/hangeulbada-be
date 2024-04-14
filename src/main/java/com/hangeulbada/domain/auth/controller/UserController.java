package com.hangeulbada.domain.auth.controller;

import com.hangeulbada.domain.auth.dto.LoginResponse;
import com.hangeulbada.domain.auth.dto.SignupResponse;
import com.hangeulbada.domain.auth.service.GoogleService;
import com.hangeulbada.domain.auth.service.KakaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Google OAuth2", description = "구글 로그인/회원가입 API")
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
    @Operation(summary = "구글 로그인", description = "구글 로그인 후 redirect되는 back url")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "401", description = "로그인 실패<br>message=\"로그인 실패\"<br>role 선택 후 회원가입(/login/oauth2/code/google/signup) 필요")
    })
    public ResponseEntity<LoginResponse> googleRedirectUri(
            @Parameter(description = "구글 로그인 후 리다이렉트 URI로 전달받은 code") @RequestParam String code)
    {
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
    @Operation(summary = "구글 회원가입", description = "구글 로그인 시 response 했던 정보에서 role 선택 후 회원가입")
    @PostMapping("/login/oauth2/code/google/signup")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원가입 성공")
    })

    public ResponseEntity<LoginResponse> googleSignup(@Parameter(description = "uid, name, email, role 필요") @RequestParam SignupResponse signupResponse) {

        log.info("LoginResponse: "+signupResponse);
        return ResponseEntity.ok(googleService.googleSignup(signupResponse));

    }
}