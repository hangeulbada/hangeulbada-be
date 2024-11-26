package com.hangeulbada.domain.workbookset.repository;

import com.hangeulbada.domain.workbookset.entity.IncorrectAnswerTag;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface IncorrectAnswerTagRepository extends MongoRepository<IncorrectAnswerTag, String> {
        int countIncorrectAnswerTagsByStudentIdAndTag(String studentId, String tag);

        @Aggregation(pipeline = {
                "{$match: {'studentId': ?0, 'tag': ?1}}",
                "{$group: {_id: null, questionIds: {$addToSet: '$questionId'}}}",
                "{$project: {questionIds: 1, _id: 0}}",
                "{$unwind: '$questionIds'}",
                "{$sample: {size: 10}}",
                "{$group: {_id: null, questionIds: {$push: '$questionIds'}}}",
                "{$project: {questionIds: 1, _id: 0}}"
        })
        QuestionIdsDTO findQuestionIdsByStudentIdAndTag(String studentId, String tag);
}