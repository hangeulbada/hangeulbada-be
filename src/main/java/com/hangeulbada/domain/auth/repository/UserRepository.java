package com.hangeulbada.domain.auth.repository;

import com.hangeulbada.domain.auth.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUid(String uid);
    User save(User user);
    void delete(User user);

}
