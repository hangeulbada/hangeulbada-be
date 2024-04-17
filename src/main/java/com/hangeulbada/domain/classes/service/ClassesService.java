package com.hangeulbada.domain.classes.service;

import com.hangeulbada.domain.classes.dto.ClassesCreateRequestDto;
import com.hangeulbada.domain.classes.dto.ClassesCreateResponseDto;
import com.hangeulbada.domain.classes.dto.ClassesResponseDto;
import org.apache.catalina.User;

import java.util.List;

public interface ClassesService {
    ClassesCreateResponseDto createClasses(User user, ClassesCreateRequestDto classesCreateRequestDto);
    List<ClassesResponseDto> getClasses();
    void deleteClasses(String classname);
}

