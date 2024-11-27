package com.hangeulbada.domain.group.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IncorrectsGroupDTO {
    private String id;
    private String groupName;
    private String description;
    private String teacherId;
    private String groupCode;
    private List<String> studentIds;
    private List<String> workbookIds;
}