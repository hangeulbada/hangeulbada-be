package com.hangeulbada.domain.workbookset.service;

import com.hangeulbada.domain.workbookset.dto.QuestionDto;
import com.hangeulbada.domain.workbookset.dto.TagCountDto;
import com.hangeulbada.domain.workbookset.entity.Tag;

import java.util.List;

public interface IncorrectAnswerService {
    // 오답 통계를 위해 태그 별 오답 수 반환
    List<TagCountDto> countIncorrects(String studentId);
    // 학생의 오답 중 특정 태그의 오답 (최대)10개 가져오기
    List<QuestionDto> getIncorrectsByTag(String studentId, Tag tag);
}
