package com.hangeulbada.domain.workbookset.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "question")
public class Question {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "workbookId", nullable = false)
    private Workbook workbook;

//    @Column(nullable = false)
    private String content;
}
