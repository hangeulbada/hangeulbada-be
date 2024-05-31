package com.hangeulbada.domain.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecificCompareDTO {
    String number;
    String answer;
    String studentAnswer;
    boolean isCorrect;
}
