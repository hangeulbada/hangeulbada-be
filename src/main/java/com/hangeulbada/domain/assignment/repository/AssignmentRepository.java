package com.hangeulbada.domain.assignment.repository;

import com.hangeulbada.domain.assignment.entity.Assignment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AssignmentRepository extends MongoRepository<Assignment, String> {

}