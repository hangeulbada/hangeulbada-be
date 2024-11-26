package com.hangeulbada.domain.assignment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "AssignmentScoreRequestDto")
public class AssignmentScoreRequestDto {
    private Map<String, String> workbook; // 문제 번호와 텍스트 매핑
    private String answer; // 이미지 URL
}
