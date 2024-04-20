package com.hangeulbada.domain.workbookset.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "Workbook")
public class Workbook {
    @Id
    private String id;
    private String userId;
    private String title;
    private Set<Question> questions = new HashSet<>();
    private LocalDateTime createdAt;
    private LocalDateTime deadline;
    private boolean activated;
}
