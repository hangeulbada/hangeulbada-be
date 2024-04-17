package com.hangeulbada.domain.ocr.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "문항별 점수")
public class ScoreDTO {
    @Schema(description = "문항 번호", example = "1")
    int number;
    @Schema(description = "문항 점수", example = "90")
    int score;
}
