package com.hangeulbada.domain.assignment.repository;

import com.hangeulbada.domain.assignment.entity.Assignment;
import com.hangeulbada.domain.group.dto.GroupAssignmentDTO;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AssignmentRepository extends MongoRepository<Assignment, String> {
    @Aggregation(pipeline = {
            "{$match: {'studentId': ?0}}",
            "{$addFields: {'workbookIdObj': {$toObjectId: '$workbookId'}}}",
            "{$lookup: {from: 'Workbook', localField: 'workbookIdObj', foreignField: '_id', as: 'workbook_info'}}",
            "{$unwind: '$workbook_info'}",
            "{$project: {workbookId: 1, workbookTitle: '$workbook_info.title', assignmentId: '$_id', score: 1}}"
    })
    List<GroupAssignmentDTO> findGroupAssignmentsByStudentId(String studentId);
}