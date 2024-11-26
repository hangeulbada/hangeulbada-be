package com.hangeulbada.domain.assignment.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Document(collection = "Assignment")
@AllArgsConstructor
@NoArgsConstructor
public class Assignment {
    @Id
    private String id;
    private String studentId; //*
    private String workbookId; //*
    private List<String> questions; // 문제들
    private List<AssignmentContent> answers; //문제 번호, 데이터
    private Integer score; //맞은 개수
    private LocalDateTime submitDate; //*
    private String imgS3Url;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "AssignmentContent")
class AssignmentContent{
    private Integer num; // 답안 번호
    private Integer simillarity; // 유사도
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