package com.hangeulbada.domain.workbookset.repository;

import com.hangeulbada.domain.workbookset.entity.IncorrectAnswerTag;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface IncorrectAnswerTagRepository extends MongoRepository<IncorrectAnswerTag, String> {
        int countIncorrectAnswerTagsByStudentIdAndTag(String studentId, String tag);

//    List<IncorrectAnswerTag> findIncorrectAnswerTagByStudentIdAndTag(String studentId, Tag tag);
}