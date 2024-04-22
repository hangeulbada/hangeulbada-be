package com.hangeulbada.domain.workbookset.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "WorkbookDto Model")
public class WorkbookDto {
    @JsonIgnore
    private String id;
    private String teacherId;
    private String title;
    private List<String> questions;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
