package com.hangeulbada.domain.workbookset.dto;

import com.hangeulbada.domain.workbookset.entity.Tag;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "QuestionRequestDto Model")
public class QuestionRequestDto {
    private String content;
    private Integer difficulty;
    private String[] tags;
}
