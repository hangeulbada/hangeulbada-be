package com.hangeulbada.domain.user.entities;

import com.hangeulbada.entities.BaseTimeEntity;
import com.hangeulbada.domain.user.enums.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter // Getter 생성
@NoArgsConstructor // Default 생성자
@Entity // Entity임을 명시
@Table(name = "users") // 테이블명 설정
public class User extends BaseTimeEntity { // BaseTimeEntity 상속
    @Id // Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) // nullable하지 않도록 설정
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING) // Enum 값 저장
    @Column(nullable = false)
    private Role role;

    @Builder // Builder Pattern 사용
    public User(String name, String email, String picture, Role role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    // update 함수 구현
    public User update(String name, String picture) {
        this.name = name;
        this.picture = picture;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}