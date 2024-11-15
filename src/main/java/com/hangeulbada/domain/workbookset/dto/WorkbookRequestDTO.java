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
    private String title;
    private String description;
    private int questionNum;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
