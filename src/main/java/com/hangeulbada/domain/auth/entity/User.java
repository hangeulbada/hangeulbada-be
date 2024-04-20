package com.hangeulbada.domain.auth.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("User")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class User{
    @Id
    private String id;
    private String uid;
    private String email;
    private String name;
    private String role;
}