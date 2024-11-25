package com.hangeulbada.domain.externalapi.service;

import com.hangeulbada.domain.assignment.dto.AssignmentScoreRequestDto;
import com.hangeulbada.domain.assignment.dto.AssignmentScoreResponseDTO;
import com.hangeulbada.domain.workbookset.dto.ClaudeRequestDto;

public interface ApiService {
    String postToClaude(ClaudeRequestDto requestDto);
    Integer calculateQuestionDiff(String text);
    AssignmentScoreResponseDTO postAssignmentScore(AssignmentScoreRequestDto requestDto);
}
