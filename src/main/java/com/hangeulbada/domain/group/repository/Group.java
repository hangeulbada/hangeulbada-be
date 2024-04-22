package com.hangeulbada.domain.group.repository;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@Document("Group")
public class Group{
    @Id
    private String id;
    private String groupName;
    private String teacherId;
    private String groupCode;
    @Builder.Default
    private List<String> studentIds = new ArrayList<>();}
