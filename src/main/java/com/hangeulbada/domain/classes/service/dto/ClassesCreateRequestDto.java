package com.hangeulbada.domain.classes.service.dto;

import com.hangeulbada.domain.classes.domain.Classes;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.apache.catalina.User;

@Data
@Getter
public class ClassesCreateRequestDto {
    private String classname;
    private String teacherId;
    @Builder
    public ClassesCreateRequestDto(String classname, String teacherId){
        this.classname = classname;
        this.teacherId = teacherId;
    }
    public Classes toEntity(User user){
        return Classes.builder()
                .classname(classname)
                .teacherId(teacherId)
                .build();
    }
}