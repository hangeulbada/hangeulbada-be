package com.hangeulbada.domain.group.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@Getter
public class GroupResponseDto {
    private String groupName;
    private String teacherId;
}
