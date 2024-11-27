package com.hangeulbada.domain.group.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document("Group")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Group{
    @Id
    private String id;
    private String groupName;
    private String description;
    private String teacherId;
    private String groupCode;
    private List<String> studentIds;
    @Builder.Default
    private List<String> workbookIds = new ArrayList<>();;
}
