package com.hangeulbada.domain.classes.service;

import com.hangeulbada.domain.classes.service.dto.ClassesCreateRequestDto;
import com.hangeulbada.domain.classes.service.dto.ClassesCreateResponseDto;
import com.hangeulbada.domain.classes.service.dto.ClassesResponseDto;
import org.apache.catalina.User;

import java.util.List;
import java.util.Optional;

public interface ClassesService {
    ClassesCreateResponseDto createClasses(User user, ClassesCreateRequestDto classesCreateRequestDto);
    List<ClassesResponseDto> getClasses();
    void deleteClasses(String classname);
}

