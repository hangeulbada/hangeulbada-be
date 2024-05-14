package com.hangeulbada.domain.group.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("Group")
@Getter
@Setter
public class Group{
    @Id
    private String id;
    private String groupName;
    private String description;
    private String teacherId;
    private String groupCode;
    private List<String> studentIds;
}
