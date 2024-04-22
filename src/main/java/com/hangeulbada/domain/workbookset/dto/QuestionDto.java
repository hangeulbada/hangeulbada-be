package com.hangeulbada.domain.workbookset.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "QuestionDto Model")
public class QuestionDto {
    private String teacherId;
    private String content;
}
