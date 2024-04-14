package com.hangeulbada.domain.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginResponse {
    private String uid="string uid";
    private String name="name";
    private String email="email";
    private String role="teacher or student";
    private AuthTokens token=new AuthTokens();
    private String message="401=로그인 실패";

    public LoginResponse(String uid, String name, String email, String role, AuthTokens token){
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.role = role;
        this.token = token;
    }
}