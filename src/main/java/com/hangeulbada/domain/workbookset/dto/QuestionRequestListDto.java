package com.hangeulbada.domain.workbookset.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRequestListDto {
    private List<String> content;
}
