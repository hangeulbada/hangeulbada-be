package com.hangeulbada.domain.auth.component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component

public class JwtTokenProvider {
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1; 	//1시간
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 14;  // 14일

    private final Key key;
    long now = (new Date()).getTime();
    public JwtTokenProvider(@Value("${custom.jwt.secretKey}") String secretKey) {

        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String accessTokenGenerate(String subject) {
        Date expiredAt = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);

        return Jwts.builder()
                .setSubject(subject)    //uid
                .setExpiration(expiredAt)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String refreshTokenGenerate() {
        Date expiredAt = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);

        return Jwts.builder()
                .setExpiration(expiredAt)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }
    public Claims parseClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public boolean isRefreshTokenValid(String token) {
        log.info("isRefreshTokenValid", token);
        log.info(isTokenExpired(token) + "");
        return !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return parseClaims(token).getExpiration().before(new Date());
    }

    public String generateAccessTokenFromRefreshToken(String refreshToken) {
        log.info("generate new token from refresh token");
        if (isRefreshTokenValid(refreshToken)) {
            return (accessTokenGenerate(getUid(refreshToken)));
        }
        return null;
    }

    public String getUid(String token) {
        return parseClaims(token).get("uid", String.class);
    }


}