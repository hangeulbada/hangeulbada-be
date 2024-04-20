package com.hangeulbada.domain.group.dto;

import com.hangeulbada.domain.group.repository.Group;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.apache.catalina.User;

@Data
@Getter
@Builder
public class GroupCreateRequestDto {
    private String groupName;
    private String teacherId;
}