package com.hangeulbada.domain.assignment.repository;

import com.hangeulbada.domain.assignment.dto.AssignmentSummaryDto;
import com.hangeulbada.domain.assignment.entity.Assignment;
import com.hangeulbada.domain.group.dto.GroupAssignmentDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

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
    List<Assignment> findByStudentIdIn(List<String> studentIds);

    @Query("{}")
    List<Assignment> findByStudentIdAndWorkbookIdOrderByCreatedDateDesc(String studentId, String workbookId, Pageable pageable);

    @Aggregation(pipeline = {
            "{$match:  {'studentId':  ?0, 'workbookId':  ?1}}",
            "{$addFields: {'workbookIdObj': {$toObjectId: '$workbookId'}}}",
            "{$lookup: {from: 'Workbook', localField: 'workbookIdObj', foreignField: '_id', as: 'workbook_info'}}",
            "{$unwind: '$workbook_info'}",
            "{$project: {workbookId: 1, workbookTitle: '$workbook_info.title', assignmentId: '$_id', score: 1, submitDate: 1}}"
    })
    List<AssignmentSummaryDto> getWorkbookAssignment(String studentId, String workbookId);

    // 편의 메소드로 사용
    default Assignment findLatestByStudentIdAndWorkbookId(String studentId, String workbookId) {
        return findByStudentIdAndWorkbookIdOrderByCreatedDateDesc(studentId, workbookId, PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "submitDate"))).stream().findFirst().orElse(null);
    }}