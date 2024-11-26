package com.hangeulbada.domain.workbookset.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagCountDto {
    private String tag;
    private long count;
}
