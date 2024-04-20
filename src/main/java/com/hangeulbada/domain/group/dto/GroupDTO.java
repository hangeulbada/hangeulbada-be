package com.hangeulbada.domain.group.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class GroupDTO {
    @Id
    private String id;
    private String groupName;
    private String teacherId;
    private String groupCode;
    @Builder.Default
    private boolean activated = false;
}
