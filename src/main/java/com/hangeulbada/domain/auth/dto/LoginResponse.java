package com.hangeulbada.domain.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginResponse {
    private String uid;
    private String name;
    private String email;
    private String role;
    private AuthTokens token;
    private String message;

    public LoginResponse(String uid, String name, String email, String role, AuthTokens token){
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.role = role;
        this.token = token;
    }
}