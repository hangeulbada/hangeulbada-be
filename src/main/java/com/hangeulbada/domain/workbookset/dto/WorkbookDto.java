package com.hangeulbada.domain.workbookset.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "WorkbookDto Model")
public class WorkbookDto {
    private String id;
    private String teacherId;
    private String title;
    private String description;
    private Integer difficulty;
    private int questionNum;
    @Builder.Default
    private List<String> questionIds = new ArrayList<>();    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
