package com.hangeulbada.domain.group.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupAttendResponse {
    private String id;
    private String groupName;
    private String description;
    private String teacherId;
    private String groupCode;
}
