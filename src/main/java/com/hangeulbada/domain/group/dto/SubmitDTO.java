package com.hangeulbada.domain.group.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmitDTO {
    private String name;
    private String studentId;
    private String workbookId;
    private String workbookTitle;
    private String assignmentId;
    private Integer score;
    private String submitDate;
}
