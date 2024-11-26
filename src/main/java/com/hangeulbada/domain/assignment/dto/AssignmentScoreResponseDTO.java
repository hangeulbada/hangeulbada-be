package com.hangeulbada.domain.assignment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "AssignmentScoreResponseDTO")
public class AssignmentScoreResponseDTO {
    private List<QuestionAnalysisDto> answers;
}
