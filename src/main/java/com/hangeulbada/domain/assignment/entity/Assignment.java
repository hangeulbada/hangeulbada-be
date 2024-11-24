package com.hangeulbada.domain.assignment.entity;

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
    private String studentId;
    private String workbookId;
    private Map<Integer, AssignmentContent> content; //문제 번호, 데이터
    private Integer score; //맞은 개수
    private LocalDateTime submitDate;
    private String imgS3Url;
}

class AssignmentContent{
    private String questionFull; //문제 전체 문장
    private String answerFull; //답안 전체 문장
    private Integer correct; //맞은 퍼센트
    private List<Analysis> analysis;
}

class Analysis{ // 답안 틀린 부분
    String question;
    String answer;
}