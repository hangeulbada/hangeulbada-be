package com.hangeulbada.domain.workbookset.service.impl;

import com.hangeulbada.domain.workbookset.dto.QuestionDto;
import com.hangeulbada.domain.workbookset.dto.TagCountDto;
import com.hangeulbada.domain.workbookset.entity.Tag;
import com.hangeulbada.domain.workbookset.repository.IncorrectAnswerTagRepository;
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
    public List<QuestionDto> getIncorrectsByTag(String studentId, Tag tag) {
//        List<QuestionDto> incorrects = incorrectsRepository.findIncorrectAnswerTagByStudentIdAndTag(studentId, tag);
//        createIncorrectsWorkbook(studentId,incorrects);
        return List.of();
    }

    public void createIncorrectsWorkbook(String studentId, List<QuestionDto> incorrects) {
        // 유저의 오답 그룹에 워크북 등록 + 문제들 등록
        return;
    }
}
