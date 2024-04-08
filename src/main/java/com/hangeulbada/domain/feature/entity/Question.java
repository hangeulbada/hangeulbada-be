package com.hangeulbada.domain.feature.entity;

import jakarta.persistence.*;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workbookId", nullable = false)
    private Workbook workbook;

    @Column(nullable = false)
    private String content;
}
