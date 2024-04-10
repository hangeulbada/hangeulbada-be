package com.hangeulbada.domain.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginResponse {
    private String id;
    private String name;
    private String email;
    private AuthTokens token;

    public LoginResponse(String id, String name, String email, AuthTokens token) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.token = token;
    }
}