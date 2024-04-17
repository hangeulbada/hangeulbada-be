package com.hangeulbada.domain.group.controller.request;

import com.hangeulbada.domain.group.dto.GroupCreateRequestDto;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GroupCreateRequest {
    @NotEmpty(message = "groupName is required")
    private String groupName;
    @NotEmpty(message = "teacherId is required")
    private String teacherId;
    public GroupCreateRequestDto toDto(){
        return GroupCreateRequestDto.builder()
                .groupName(groupName)
                .teacherId(teacherId)
                .build();
    }
}
