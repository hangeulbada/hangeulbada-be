package com.hangeulbada.domain.auth.controller;

import com.hangeulbada.domain.auth.component.UnsecuredAPI;
import com.hangeulbada.domain.auth.dto.LoginResponse;
import com.hangeulbada.domain.auth.dto.SignupResponse;
import com.hangeulbada.domain.auth.service.GoogleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class AuthController {

    private final GoogleService googleService;

    @ResponseBody
    @GetMapping("/login/oauth2/code/google")
    @UnsecuredAPI
    @Operation(summary = "구글 로그인", description = "구글 로그인 후 redirect되는 back url")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공",
            content = @Content(schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "401", description = "회원가입 필요",
            content = @Content(schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "404", description = "유효하지 않은 인가 code")
    })
    public ResponseEntity<?> googleRedirectUri(
            @Parameter(description = "구글 로그인 후 리다이렉트 URI로 전달받은 code") @RequestParam String code)
    {
        try {
            LoginResponse response = googleService.googleOauth2(code);
            // 로그인 성공
            if (response.getToken()!=null){
                return ResponseEntity.ok(response);
            }
            // 회원가입 필요
            else{
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        }
        // 유효하지 않은 code
        catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "유효하지 않은 code입니다.");
        }
    }

    @ResponseBody
    @Operation(summary = "구글 회원가입", description = "구글 로그인 시 response 했던 정보에서 role 선택 후 회원가입")
    @PostMapping("/login/oauth2/code/google/signup")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원가입 성공")
    })
    @UnsecuredAPI
    public ResponseEntity<LoginResponse> googleSignup(@RequestBody SignupResponse signupResponse) {
        return ResponseEntity.ok(googleService.googleSignup(signupResponse));

    }
}