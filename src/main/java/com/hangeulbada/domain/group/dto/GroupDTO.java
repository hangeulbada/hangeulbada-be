package com.hangeulbada.domain.group.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class GroupDTO {
    @Id
    private String id;
    private String groupName;
    private String teacherId;
    private String groupCode;
    private boolean activated = false;
}
