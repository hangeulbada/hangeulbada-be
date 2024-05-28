package com.hangeulbada.domain.group.repository;

import com.hangeulbada.domain.group.entity.Group;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends MongoRepository<Group, String> {

    List<Group> findByTeacherId(String teacherId);

    Optional<Group> findByGroupCode(String code);

    List<Group> findByStudentIdsContaining(String studentId);
}
