package com.hangeulbada.domain.group.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends MongoRepository<Group,String> {
    Optional<Group> findByGroupName(String id);
}
