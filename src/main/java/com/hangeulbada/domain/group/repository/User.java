package com.hangeulbada.domain.group.repository;

import org.springframework.data.annotation.Id;
import lombok.*;

@Getter@Setter
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
    private String loginType;
}
