package com.hangeulbada.domain.auth.dto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> { // User 엔티티의 ID 필드 타입으로 변경해주세요
//    User findByEmail(String email);
    User findByUid(Long uid);
    User save(User user);
    void delete(User user);

}
