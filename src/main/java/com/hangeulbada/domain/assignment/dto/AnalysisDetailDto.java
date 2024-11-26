package com.hangeulbada.domain.assignment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "AnalysisDetailDto")
public class AnalysisDetailDto {
    private String question;               // 질문 텍스트
    private String answer;                 // 학생 답변
    private List<String> pronounce;        // 발음 규칙
}
