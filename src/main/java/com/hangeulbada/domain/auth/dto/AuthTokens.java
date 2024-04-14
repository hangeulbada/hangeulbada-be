package com.hangeulbada.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "토큰 정보")
public class AuthTokens {
    @Schema(description = "액세스 토큰", example = "ey...token")
    private String accessToken;
    @Schema(description = "리프레시 토큰", example = "ey...token")
    private String refreshToken;
    @Schema(description = "토큰 타입", example="Bearer")
    private String grantType;
    @Schema(description = "토큰 만료 시간", example="3600")
    private Long expiresIn;

    public static AuthTokens of(String accessToken, String refreshToken, String grantType, Long expiresIn) {
        return new AuthTokens(accessToken, refreshToken, grantType, expiresIn);
    }
}