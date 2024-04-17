package com.hangeulbada.domain.group.dto;

import com.hangeulbada.domain.group.repository.Group;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class GroupResponseDto {
    private String groupName;
    private String teacherId;

    @Builder
    public GroupResponseDto(String groupname, String teacherId){
        this.groupName = groupname;
        this.teacherId = teacherId;
    }
    public static GroupResponseDto from(Group group){
        return GroupResponseDto.builder()
                .groupname(group.getGroupName())
                .teacherId(group.getTeacherId())
                .build();
    }
}
