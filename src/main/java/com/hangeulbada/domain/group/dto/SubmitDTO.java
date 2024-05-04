package com.hangeulbada.domain.group.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubmitDTO {
    private String name;
    private String workbookId;
    private String workbookTitle;
    private String assignmentId;
    private String score;
    private String submitDate;
}
