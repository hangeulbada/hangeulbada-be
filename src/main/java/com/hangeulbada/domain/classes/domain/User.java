package com.hangeulbada.domain.classes.domain;

import jakarta.persistence.Id;
import lombok.*;

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
    private String role = "undefined";
    private String loginType;
}
