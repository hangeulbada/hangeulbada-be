package com.hangeulbada.domain.workbookset.dto;

import com.hangeulbada.domain.workbookset.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TagCountDto {
    private Tag tagId;
    private long count;
}
