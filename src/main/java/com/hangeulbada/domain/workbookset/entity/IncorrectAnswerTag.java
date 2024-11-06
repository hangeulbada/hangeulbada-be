package com.hangeulbada.domain.workbookset.entity;

import lombok.*;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "IncorrectAnswerTag")
public class IncorrectAnswerTag {
    @Id
    private String id;
    private String studentId;
    private Tag tag;
    private String questionId;
}
