package com.hangeulbada.domain.auth.dto;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    private String id;
    private String uid;
    private String email;
    private String name;
    private String role;
}
