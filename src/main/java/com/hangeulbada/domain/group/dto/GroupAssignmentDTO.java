package com.hangeulbada.domain.group.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Schema (description = "학생이 클래스에서 푼 문제집")
public class GroupAssignmentDTO {
    private String workbookId;
    private String workbookTitle;
    private String assignmentId;
    private String score;
}
