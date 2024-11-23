package com.hangeulbada.domain.workbookset.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "QuestionResponseDto Model")
public class QuestionResponseDto {
    private String id;
    private String teacherId;
    private String content;
    private String[] tags;
    private String audioFilePath;
}
