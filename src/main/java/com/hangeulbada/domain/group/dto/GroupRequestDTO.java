package com.hangeulbada.domain.group.dto;

import com.hangeulbada.domain.group.repository.Group;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Builder
public class GroupRequestDTO {
    private String groupName;
    private String teacherId;

    public Group toEntity(){
        return Group.builder()
                .groupName(this.groupName)
                .teacherId(this.teacherId)
                .build();
    }
}