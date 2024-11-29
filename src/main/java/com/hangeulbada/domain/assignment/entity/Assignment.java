package com.hangeulbada.domain.assignment.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "Assignment")
@Schema(description = "Assignment")
@AllArgsConstructor
@NoArgsConstructor
public class Assignment {
    @Id
    private String id;
    private String studentId; //*
    private String workbookId; //*
    private List<AssignmentContent> answers; //문제 번호, 데이터
    private Integer score; //맞은 개수
    private LocalDateTime submitDate; //*
    private String imgS3Url;
}