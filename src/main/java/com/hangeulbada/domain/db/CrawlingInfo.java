package com.hangeulbada.domain.db;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "interpark") // 실제 몽고 DB 컬렉션 이름
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CrawlingInfo {
    @Id
    private String id;
    private String name;
    private Long age;
}