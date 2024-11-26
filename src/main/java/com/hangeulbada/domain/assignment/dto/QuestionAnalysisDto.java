package com.hangeulbada.domain.assignment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "QuestionAnalysisDto")
public class QuestionAnalysisDto{
    private Integer num; // 답안 번호
    private Integer simillarity; // 유사도
    private String ocr_answer;
    private List<AnalysisDetailDto> analysis;
}
