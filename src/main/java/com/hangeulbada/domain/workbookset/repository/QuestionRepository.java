package com.hangeulbada.domain.workbookset.repository;

import com.hangeulbada.domain.workbookset.entity.Question;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuestionRepository extends MongoRepository<Question, String> { }
