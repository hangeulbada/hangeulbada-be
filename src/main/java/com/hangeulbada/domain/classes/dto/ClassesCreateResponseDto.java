package com.hangeulbada.domain.classes.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
@Getter
public class ClassesCreateResponseDto {
    private String classcode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public ClassesCreateResponseDto(String classcode, LocalDateTime createdAt, LocalDateTime updatedAt ){
        this.classcode = classcode;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
