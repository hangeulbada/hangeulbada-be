package com.hangeulbada.domain.workbookset.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "Workbook")
public class Workbook{
    @Id
    private String id;
    private String teacherId;
    private String title;
    private String description;
    @Builder.Default
    private Integer difficulty = 0;
    private int questionNum;
    @Builder.Default
    private List<String> questionIds = new ArrayList<>();
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
