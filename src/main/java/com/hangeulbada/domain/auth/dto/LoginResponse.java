package com.hangeulbada.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Schema(description = "로그인 응답")
public class LoginResponse {
    @Schema(description = "사용자 id (한글바다 id)", example = "107578628897984858117")
    private String id;
    @Schema(description = "사용자 uid (Google id)", example = "598237442")
    private String uid;
    @Schema(description = "사용자 이름", example = "홍길동")
    private String name;
    @Schema(description = "사용자 이메일", example = "babda@google.com")
    private String email;
    @Schema(description = "사용자 역할", example = "teacher")
    private String role;
    @Schema(description = "JWT 토큰")
    private AuthTokens token;
}