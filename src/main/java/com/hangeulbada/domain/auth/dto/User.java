package com.hangeulbada.domain.auth.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    private Long id;
    private Long uid;
//    private String nickname;
//    private String email;
//    private String password;
//    private String role;
//    private String provider;
//    private String providerId;
//    private String profileImage;
//    private String profileImageThumbnail;
//    private String introduction;
//    private String website;
//    private String phone;
    private String LoginType;
}
