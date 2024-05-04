package com.hangeulbada.domain.assignment.repository;

import com.hangeulbada.domain.assignment.entity.Assignment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AssignmentRepository extends MongoRepository<Assignment, String> {
    List<Assignment> findByStudentIdIn(List<String> studentIds);
}