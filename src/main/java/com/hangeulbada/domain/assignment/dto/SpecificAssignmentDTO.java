package com.hangeulbada.domain.assignment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "SpecificAssignmentDTO")
public class SpecificAssignmentDTO {
    private String studentName;
    private List<String> questions; // 문제들
    private List<AssignmentContentDto> answers; // 답안 관련
    private Integer score; //맞은 개수
    private String imgS3Url;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "AssignmentContentDto")
class AssignmentContentDto{
    private Integer num; // 답안 번호
    private Integer simillarity; // 유사도
    private String ocr_answer;
    private List<AnalysisDto> analysis;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "AnalysisDto")
class AnalysisDto{ // 답안 틀린 부분
    String question;
    String answer;
}
