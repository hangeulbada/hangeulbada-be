package com.hangeulbada.domain.group.repository;

import com.hangeulbada.domain.common.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//@Table(name = "classes")
@Getter
@Setter
@Builder
@Document("Group")
public class Group extends BaseTimeEntity {
    @Id
    private String id;
    private String groupName;
    private String teacherId;
    private String groupCode;

    private boolean activated = false;
}
