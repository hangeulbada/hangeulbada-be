package com.hangeulbada.domain.workbookset.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "QuestionDto Model")
public class QuestionDto {
    private String id;
//    @NotEmpty(message = "문제는 한 글자 이상이어야 합니다.")
    private String content;
}
