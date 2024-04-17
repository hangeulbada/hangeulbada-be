package com.hangeulbada.domain.workbookset.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "workbook")
public class Workbook {
    @Id
    private String id;

//    @Column(nullable = false)
    private String userId;

//    @Column(nullable = false)
    private String title;

//    @OneToMany(mappedBy = "workbook", cascade = CascadeType.ALL)
    private Set<Question> questions = new HashSet<>();

//  생성시 시간으로 자동 설정
//    @Column(nullable = false)
    private LocalDateTime createdAt;

//    @Column(nullable = true)
    private LocalDateTime deadline;

//    @Column(nullable = false)
    private boolean activated;
}
