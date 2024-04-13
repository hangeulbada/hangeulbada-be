package com.hangeulbada.domain.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignupResponse {
    private String uid;
    private String name;
    private String email;
    private String role;
}