package com.hangeulbada.domain.workbookset.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Schema(
        description = "WorkbookDto Model"
)
public class WorkbookDto {
    private Long id;
    @NotEmpty
    private Long userId;

    @NotEmpty
    @Size(min=2, message = "제목은 최소 2글자 이상이어야 합니다.")
    private String title;

    private Set<QuestionDto> questions;

    private LocalDateTime deadline;

    @NotEmpty // default value - true
    private boolean activated;

}
