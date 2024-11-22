package com.hangeulbada.domain.workbookset.dto;

import com.hangeulbada.domain.workbookset.entity.Tag;
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
@Schema(description = "QuestionDto Model")
public class QuestionDto {
    @Id
    private String id;
    private String teacherId;
    private String content;
    private Integer difficulty;
    private String[] tags;
    private String audioFilePath;
}
