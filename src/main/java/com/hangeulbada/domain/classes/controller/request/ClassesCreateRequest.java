package com.hangeulbada.domain.classes.controller.request;

import com.hangeulbada.domain.classes.service.dto.ClassesCreateRequestDto;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClassesCreateRequest {
    @NotEmpty(message = "classname is required")
    private String classname;
    @NotEmpty(message = "teacherId is required")
    private String teacherId;
    public ClassesCreateRequestDto toDto(){
        return ClassesCreateRequestDto.builder()
                .classname(classname)
                .teacherId(teacherId)
                .build();
    }
}
