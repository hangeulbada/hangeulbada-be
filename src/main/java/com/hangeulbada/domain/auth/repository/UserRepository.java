package com.hangeulbada.domain.auth.repository;

import com.hangeulbada.domain.auth.dto.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> { // User 엔티티의 ID 필드 타입으로 변경해주세요
    Optional<User> findByUid(String uid);


    User save(User user);
    void delete(User user);

}
