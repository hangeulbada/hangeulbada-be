package com.hangeulbada.domain.assignment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "AnalysisDetailDto")
public class AnalysisDetailDto {
    private String question;               // 질문 텍스트
    private String answer;                 // 학생 답변
    private List<String> pronounce;        // 발음 규칙
}
