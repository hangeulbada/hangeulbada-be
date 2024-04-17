package com.hangeulbada.domain.classes.service;
import com.hangeulbada.domain.classes.repository.Classes;
import com.hangeulbada.domain.classes.repository.ClassesRepository;
import com.hangeulbada.domain.classes.dto.ClassesCreateRequestDto;
import com.hangeulbada.domain.classes.dto.ClassesCreateResponseDto;
import com.hangeulbada.domain.classes.dto.ClassesResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class ClassesServiceImpl implements ClassesService{
    private final ClassesRepository classesRepository;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";

    private String generateClassCode(){
        StringBuilder codeBuilder = new StringBuilder();
        Random random = new Random();
        for(int i=0;i<6;i++){
            char randomChar = CHARACTERS.charAt(random.nextInt(CHARACTERS.length()));
            codeBuilder.append(randomChar);
        }

        return codeBuilder.toString();
    }
    @Override
    @Transactional
    public ClassesCreateResponseDto createClasses(User user, ClassesCreateRequestDto requestDto) {
        String classCode = generateClassCode();
        Classes classes = requestDto.toEntity(user);
        classes.setClasscode(classCode);
        classesRepository.save(classes);
        ClassesCreateResponseDto responseDto = ClassesCreateResponseDto.builder()
                .classcode(classCode)
                .createdAt(classes.getCreatedAt())
                .updatedAt(classes.getUpdatedAt())
                .build();
        return responseDto;
    }
    @Override
    @Transactional
    public List<ClassesResponseDto> getClasses() {
        List<ClassesResponseDto> classes = classesRepository.findAll()
                .stream().map(ClassesResponseDto::from).toList();
        return classes;
    }
    @Override
    @Transactional
    public void deleteClasses(String classname){
        Classes classes = classesRepository.findByClassname(classname).orElse(null);
        classesRepository.delete(classes);
    }
}
