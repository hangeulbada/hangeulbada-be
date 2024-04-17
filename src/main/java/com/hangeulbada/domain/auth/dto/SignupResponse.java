package com.hangeulbada.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "회원가입 응답")
public class SignupResponse {
    @Schema(description = "사용자 uid", example = "598237442")
    private String uid;
    @Schema(description = "사용자 이름", example = "홍길동")
    private String name;
    @Schema(description = "사용자 이메일", example = "babda@google.com")
    private String email;
    @Schema(description = "사용자 역할", example = "teacher")
    private String role;
}