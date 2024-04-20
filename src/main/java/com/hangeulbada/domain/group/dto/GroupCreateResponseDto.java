package com.hangeulbada.domain.group.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
@Getter
@Builder

public class GroupCreateResponseDto {
    private String groupCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
