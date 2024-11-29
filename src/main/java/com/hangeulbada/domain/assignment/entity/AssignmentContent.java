package com.hangeulbada.domain.assignment.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "AssignmentContent")
@Schema(description = "AssignmentContent")
public class AssignmentContent{
    private Integer num; // 답안 번호
    private Integer simillarity; // 유사도
    private String questionFull; //정답 문장
    private String ocr_answer;
    private List<Analysis> analysis;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Analysis")
class Analysis{ // 답안 틀린 부분
    String question;
    String answer;
}