package com.hangeulbada.domain.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecificAssignmentDTO {
    String imgS3Url;
    List<SpecificCompareDTO> answers;
    String score;
}
