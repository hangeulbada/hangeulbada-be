package com.hangeulbada.domain.workbookset.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkbookRequestDTO {
    private String teacherId;
    private String title;
    private String description;
    private double difficulty;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
