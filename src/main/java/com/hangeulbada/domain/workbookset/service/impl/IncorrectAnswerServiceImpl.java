package com.hangeulbada.domain.workbookset.service.impl;

import com.hangeulbada.domain.workbookset.dto.*;
import com.hangeulbada.domain.workbookset.entity.Tag;
import com.hangeulbada.domain.workbookset.entity.Workbook;
import com.hangeulbada.domain.workbookset.exception.NoIncorrectsException;
import com.hangeulbada.domain.workbookset.repository.IncorrectAnswerTagRepository;
import com.hangeulbada.domain.workbookset.repository.QuestionIdsDTO;
import com.hangeulbada.domain.workbookset.service.IncorrectAnswerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class IncorrectAnswerServiceImpl implements IncorrectAnswerService {

    public final IncorrectAnswerTagRepository incorrectsRepository;
    public final TagServiceImpl tagService;
    private final ModelMapper mapper;
    public static final List<String> tagNames = Collections.unmodifiableList(
            new ArrayList<>(Arrays.asList("구개음화", "연음화", "경음화", "유음화", "비음화", "음운규칙 없음", "겹받침 쓰기", "기식음화"))
    );

    @Override
    public List<TagCountDto> countIncorrects(String studentId) {
        List<TagCountDto> tagCountDtos = new ArrayList<>();
        for(String tag:tagNames){
            tagCountDtos.add(
                    TagCountDto.builder().tag(tag)
                            .count(incorrectsRepository.countIncorrectAnswerTagsByStudentIdAndTag(studentId, tag))
                            .build()
            );
        }
        return tagCountDtos;
    }

    @Override
    public List<String> getIncorrectsByTag(String studentId, TagRequestDto tagRequestDto) {
        QuestionIdsDTO questionIdsDTO = incorrectsRepository.findQuestionIdsByStudentIdAndTag(studentId, tagRequestDto.getTagName());
        if (questionIdsDTO == null) {
            throw new NoIncorrectsException(tagRequestDto.getTagName(), studentId);
        }
//        List<QuestionDto> incorrects = incorrectsRepository.findIncorrectAnswerTagByStudentIdAndTag(studentId, tag);
//        createIncorrectsWorkbook(studentId,incorrects);
        return questionIdsDTO.getQuestionIds();
    }

    public WorkbookIdResponseDto createIncorrectsWorkbook(String studentId, TagRequestDto tagRequestDto){
        // 오답 태그로 이뤄진 문장 가져오기
        List<String> questionIds = getIncorrectsByTag(studentId, tagRequestDto);
        // 오답 그룹 있으면 거기에, 없으면 새로 생성

        // 문제집 생성 후 문제들 등록, 저장
        Workbook workbook = Workbook.builder().build();
        // 그룹에 문제집 등록

        // 문제집 id 리턴
        return mapper.map(workbook, WorkbookIdResponseDto.class);
    }

    public void createIncorrectsWorkbook(String studentId, List<QuestionDto> incorrects) {
        // 유저의 오답 그룹에 워크북 등록 + 문제들 등록
        return;
    }
}
