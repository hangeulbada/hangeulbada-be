package com.hangeulbada.domain.workbookset.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "QuestionsDto Model")
public class QuestionsDto {
    private String teacherId;
    private List<String> content;
}
