package com.hangeulbada.domain.group.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Builder
public class GroupRequestDTO {
    private String groupName;
    private String teacherId;
}