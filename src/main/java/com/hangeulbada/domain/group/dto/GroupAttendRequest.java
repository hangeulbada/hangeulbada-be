package com.hangeulbada.domain.group.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupAttendRequest {
    @Schema(description = "그룹 코드", example = "code")
    private String groupCode;
}
