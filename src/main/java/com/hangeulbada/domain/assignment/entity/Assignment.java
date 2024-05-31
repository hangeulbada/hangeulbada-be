package com.hangeulbada.domain.assignment.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
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
    private Map<Integer, String> content;
    private String score;
    private LocalDateTime submitDate;
    private String imgS3Url;
}
