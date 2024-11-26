package com.hangeulbada.domain.assignment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "문항별 유사도")
public class ScoreDTO {
    @Schema(description = "문항 번호", example = "1")
    Integer number;
    @Schema(description = "문항 유사도", example = "80")
    Integer simillarity;
}
