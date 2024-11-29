package com.hangeulbada.domain.workbookset.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;
@Getter
@Schema(description = "AiGeneratedQuestionsDto Model")
public class AiGeneratedQuestionsDto {
    Integer difficulty;
    List<QuestionContentResponseDto> questions;
}
