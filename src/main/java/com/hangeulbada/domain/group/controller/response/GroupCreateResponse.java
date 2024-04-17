package com.hangeulbada.domain.group.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hangeulbada.domain.group.dto.GroupCreateResponseDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class GroupCreateResponse {
    private final String groupCode;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "Asia/Seoul"
    )
    private final LocalDateTime createdAt;
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "Asia/Seoul"
    )
    private  final LocalDateTime updatedAt;

    public static GroupCreateResponse of(GroupCreateResponseDto dto){
        return GroupCreateResponse.builder()
                .groupCode(dto.getGroupCode())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }
}
