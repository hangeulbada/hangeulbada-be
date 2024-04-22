package com.hangeulbada.domain.auth.dto;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("User")
public class User {

    @Id
    private String id;
    private String uid;
    private String email;
    private String name;
    private String role;
}
