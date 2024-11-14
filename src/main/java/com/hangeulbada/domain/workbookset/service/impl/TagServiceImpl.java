package com.hangeulbada.domain.workbookset.service.impl;
import com.hangeulbada.domain.workbookset.entity.Tag;
import com.hangeulbada.domain.workbookset.repository.IncorrectAnswerTagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TagServiceImpl {
    public final IncorrectAnswerTagRepository incorrectAnswerTagRepository;

    public Set<Tag> validateAndConvertTags(String[] inputTags){
        Set<String> uniqueTagNames = new HashSet<>(Arrays.asList(inputTags));
        Set<Tag> validTags = uniqueTagNames.stream()
                .map(this::convertToTag).filter(Objects::nonNull).collect(Collectors.toSet());
        return validTags;
    }

    private Tag convertToTag(String tagName){
        try{
            //Enum 값과 동일시 변환, 아니면 예외
            return Tag.valueOf(tagName);
        }catch (IllegalArgumentException e) {
            return null;
        }
    }
}