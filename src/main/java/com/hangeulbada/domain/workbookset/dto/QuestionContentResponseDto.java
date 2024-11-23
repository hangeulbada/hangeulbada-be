package com.hangeulbada.domain.workbookset.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Schema(description = "QuestionContentResponseDto Model")
public class QuestionContentResponseDto {
    private String content;
    private String[] tags;
}
