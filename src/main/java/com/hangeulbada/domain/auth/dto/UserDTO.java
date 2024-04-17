package com.hangeulbada.domain.auth.dto;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO{
    @Id
    private String id;
    private String uid;
    private String email;
    private String name;
    private String role = "undefined";
}
