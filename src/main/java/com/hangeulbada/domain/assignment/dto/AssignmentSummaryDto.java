package com.hangeulbada.domain.assignment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(description = "학생이 푼 답안들")
@Document
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentSummaryDto {
    @Field("workbookId")
    private String workbookId;

    @Field("workbookTitle")
    private String workbookTitle;

    @Field("assignmentId")
    private String assignmentId;

    @Field("score")
    private Integer score;

    @Field("submitDate")
    private LocalDateTime submitDate;
}
