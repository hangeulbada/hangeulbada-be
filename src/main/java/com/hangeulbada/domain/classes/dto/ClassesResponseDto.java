package com.hangeulbada.domain.classes.dto;

import com.hangeulbada.domain.classes.repository.Classes;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class ClassesResponseDto {
    private String classname;
    private String teacherId;

    @Builder
    public ClassesResponseDto(String classname, String teacherId){
        this.classname = classname;
        this.teacherId = teacherId;
    }
    public static ClassesResponseDto from(Classes classes){
        return ClassesResponseDto.builder()
                .classname(classes.getClassname())
                .teacherId(classes.getTeacherId())
                .build();
    }
}
