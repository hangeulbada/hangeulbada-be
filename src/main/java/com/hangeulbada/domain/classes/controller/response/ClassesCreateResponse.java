package com.hangeulbada.domain.classes.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hangeulbada.domain.classes.service.dto.ClassesCreateResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ClassesCreateResponse {
    private final String classcode;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "Asia/Seoul"
    )
    private final LocalDateTime createdAt;
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "Asia/Seoul"
    )
    private  final LocalDateTime updatedAt;

    @Builder
    public ClassesCreateResponse(String classcode, LocalDateTime createdAt, LocalDateTime updatedAt){
        this.classcode = classcode;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public static ClassesCreateResponse of(ClassesCreateResponseDto dto){
        return ClassesCreateResponse.builder()
                .classcode(dto.getClasscode())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }
}
