package com.hangeulbada.domain.workbookset.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "IncorrectAnswerTagDto Model")
public class IncorrectAnswerTagDto {
    @Id
    private String id;
    private String studentId;
    private String tag;
    private String questionId;
}
