package com.hangeulbada.domain.group.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Builder
@Data
@Schema (description = "학생이 클래스에서 푼 문제집")
@Document
@NoArgsConstructor
@AllArgsConstructor
public class GroupAssignmentDTO {
    @Field("workbookId")
    private String workbookId;

    @Field("workbookTitle")
    private String workbookTitle;

    @Field("assignmentId")
    private String assignmentId;

    @Field("score")
    private Integer score;
}