package com.hangeulbada.domain.assignment.entity;

import lombok.Data;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@Document(collection = "Assignment")
public class Assignment {
    @Id
    private String id;
    private String studentId;
    private String workbookId;
    private Map<Integer, Boolean> string;
    private Map<Integer, String> content;
}
