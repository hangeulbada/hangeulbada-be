package com.hangeulbada.domain.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentDTO {
    private String id;
    private String studentId;
    private String workbookId;
    private Map<Integer, String> content;
    private String score;
    private LocalDateTime submitDate;
}
