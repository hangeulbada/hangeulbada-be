package com.hangeulbada.domain.workbookset.repository;

import com.hangeulbada.domain.workbookset.dto.TagCountDto;
import com.hangeulbada.domain.workbookset.entity.IncorrectAnswerTag;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IncorrectAnswerTagRepository extends MongoRepository<IncorrectAnswerTag, String> {


    @Aggregation(pipeline = {
            "{ '$match': { 'studentId': ?0 } }",
            "{ '$group':  { '_id':  '$tagId', 'count': { '$sum':  1 } } }"
    })
    List<TagCountDto> countIncorrectAnswersByTagForStudent(String studentId);

//    List<IncorrectAnswerTag> findIncorrectAnswerTagByStudentIdAndTag(String studentId, Tag tag);
}