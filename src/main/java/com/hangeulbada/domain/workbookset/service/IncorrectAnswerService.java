package com.hangeulbada.domain.workbookset.service;

import com.hangeulbada.domain.group.dto.IncorrectsGroupDTO;
import com.hangeulbada.domain.workbookset.dto.TagCountDto;
import com.hangeulbada.domain.workbookset.dto.TagRequestDto;
import com.hangeulbada.domain.workbookset.dto.WorkbookDto;
import com.hangeulbada.domain.workbookset.dto.WorkbookIdResponseDto;
import com.hangeulbada.domain.workbookset.repository.QuestionIdsDTO;

import java.util.List;

public interface IncorrectAnswerService {
    // 오답 통계를 위해 태그 별 오답 수 반환
    List<TagCountDto> countIncorrects(String studentId);
    // 오답 10문제 출력
    List<String> getIncorrectQids(String studentId, TagRequestDto tagRequestDto);
    // 학생의 오답 중 특정 태그의 오답 (최대)10개 가져오기
    QuestionIdsDTO getIncorrectsByTag(String studentId, String tagName);
    //오답 문제집 생성
    WorkbookIdResponseDto createIncorrectsWorkbook(String studentId, TagRequestDto tagRequestDto);
    //오답 그룹 리턴
    IncorrectsGroupDTO getMyReviewGroup(String studentId);
    //모든 오답 문제집 리스트
    List<WorkbookDto> getReviewWorkbooks(String studentId);
}
