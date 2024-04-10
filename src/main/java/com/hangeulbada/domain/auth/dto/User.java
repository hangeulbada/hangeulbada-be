package com.hangeulbada.domain.auth.dto;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "user")
public class User {

    @Id
    private Long id;
    private String uid;
    private String email;
    private String name;
    private String loginType;
}
