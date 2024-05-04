package com.hangeulbada.domain.assignment.service;

import com.hangeulbada.domain.assignment.dto.ScoreDTO;
import com.hangeulbada.domain.assignment.entity.Assignment;
import com.hangeulbada.domain.assignment.repository.AssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentService {
    private final AssignmentRepository assignmentRepository;

    public List<Assignment> getAssignmentByStudentIds(List<String> studentIds, String groupId) {
        return assignmentRepository.findByStudentIdIn(studentIds);
    }

    public List<ScoreDTO> getScores(String studentId, String workbookId, List<String> ocrText) {
        return null;
    }
}
