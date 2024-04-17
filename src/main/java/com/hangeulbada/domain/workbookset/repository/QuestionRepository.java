package com.hangeulbada.domain.workbookset.repository;

import com.hangeulbada.domain.workbookset.entity.Question;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface QuestionRepository extends MongoRepository<Question, String> {
    List<Question> findByWorkbookId(String workbookId);
}
