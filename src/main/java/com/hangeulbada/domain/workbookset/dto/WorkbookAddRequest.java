package com.hangeulbada.domain.workbookset.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkbookAddRequest {
    @Schema(description = "문제집 아이디", example = "workbookId")
    private String workbookId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}