package com.hangeulbada.domain.workbookset.repository;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@Schema(description = "학생이 클래스에서 푼 문제집")
@Document
@NoArgsConstructor
@AllArgsConstructor
public class QuestionIdsDTO {
    @Field("questionIds")
    @Builder.Default
    private List<String> questionIds = new ArrayList<>();
}
