package com.hangeulbada.domain.workbookset.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Question")
public class Question {
    @Id
    private String id;
    private String teacherId;
    private String content;
    private Integer difficulty;
    private String[] tags;
    private String audioFilePath;

}
