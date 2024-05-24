package com.hangeulbada.domain.group.repository;

import com.hangeulbada.domain.group.dto.SubmitDTO;
import com.hangeulbada.domain.group.entity.Group;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends MongoRepository<Group, String> {
    @Aggregation(pipeline = {
            "{ $match: { '_id': ?0 } }",
            "{ $lookup: { from: 'User', localField: 'studentIds', foreignField: '_id', as: 'students' } }",
            "{ $unwind: '$students' }",
            "{ $lookup: { from: 'Assignment', localField: 'students._id', foreignField:  'studentId', as: 'assignments' } }",
            "{ $unwind: '$assignments' }",
            "{ $lookup: { from: 'Workbook', localField:'assignments.workbookId', foreignField: '_id', as: 'workbooks' } }",
            "{ $unwind: '$workbooks' }",
            "{ $project: { " +
                    "name: '$students.name', " +
                    "workbookId: '$workbooks._id', " +
                    "workbookTitle: '$workbooks.title', " +
                    "assignmentId: '$assignments._id', " +
                    "score: '$assignments.score', " +
                    "submitDate: { $dateToString: { format: '%Y-%m-%d', date: '$assignments.submitDate' } }" +
                    " } }",
            "{ $sort: { submitDate: 1 } }"
    })
    List<SubmitDTO> getRecentSubmit(String groupId);
    List<Group> findByTeacherId(String teacherId);

    Optional<Group> findByGroupCode(String code);

    List<Group> findByStudentIdsContaining(String studentId);
}
