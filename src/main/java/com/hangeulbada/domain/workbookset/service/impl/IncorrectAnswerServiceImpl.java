package com.hangeulbada.domain.workbookset.service.impl;

import com.hangeulbada.domain.workbookset.dto.IncorrectAnswerTagDto;
import com.hangeulbada.domain.workbookset.dto.QuestionDto;
import com.hangeulbada.domain.workbookset.dto.TagCountDto;
import com.hangeulbada.domain.workbookset.entity.IncorrectAnswerTag;
import com.hangeulbada.domain.workbookset.entity.Tag;
import com.hangeulbada.domain.workbookset.repository.IncorrectAnswerTagRepository;
import com.hangeulbada.domain.workbookset.service.IncorrectAnswerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class IncorrectAnswerServiceImpl implements IncorrectAnswerService {

    public final IncorrectAnswerTagRepository incorrectsRepository;
    public final TagServiceImpl tagService;
    private final ModelMapper mapper;

    @Override
    public List<TagCountDto> countIncorrects(String studentId) {
        return incorrectsRepository.countIncorrectAnswersByTagForStudent(studentId);
    }

    @Override
    public void saveIncorrectAnswerTags(String studentId, String[] inputTags, String questionId) {
//        Set<Tag> tags = tagService.validateAndConvertTags(inputTags);
//        for(Tag tag: tags){
//            incorrectsRepository.save(mapper.map(IncorrectAnswerTagDto.builder().studentId(studentId).questionId(questionId).build(), IncorrectAnswerTag.class));
//        }
        for(String tag : inputTags) {
            incorrectsRepository.save(mapper.map(IncorrectAnswerTagDto.builder().studentId(studentId).questionId(questionId).build(), IncorrectAnswerTag.class));
        }
    }

    @Override
    public List<QuestionDto> getIncorrectsByTag(String studentId, Tag tag) {
//        incorrectsRepository.findIncorrectAnswerTagByStudentIdAndTag(studentId, tag);
        return List.of();
    }
}
