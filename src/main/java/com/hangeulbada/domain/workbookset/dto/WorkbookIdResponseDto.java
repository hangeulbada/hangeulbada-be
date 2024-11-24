package com.hangeulbada.domain.workbookset.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "WorkbookIdResponseDto Model")
public class WorkbookIdResponseDto {
    private String id;
}