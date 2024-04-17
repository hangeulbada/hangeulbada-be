package com.hangeulbada.domain.classes.controller;
import com.hangeulbada.domain.annotation.LoggedInUser;
import com.hangeulbada.domain.classes.controller.request.ClassesCreateRequest;
import com.hangeulbada.domain.classes.controller.response.ClassesCreateResponse;
import com.hangeulbada.domain.classes.service.ClassesService;
import com.hangeulbada.domain.classes.service.dto.ClassesResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/classes")
public class ClassesApiController {
    private final ClassesService classesService;
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    ClassesCreateResponse createClasses
            (       @LoggedInUser User user,
                    @Valid @RequestBody ClassesCreateRequest request
            ){
        return ClassesCreateResponse.of(classesService.createClasses(user,request.toDto()));
    }
    @GetMapping
    List<ClassesResponseDto> getClasses(){
        List<ClassesResponseDto> classes = classesService.getClasses();
        return classes;
    }
    @DeleteMapping("/delete/{classname}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteClasses(@PathVariable String classname){
        classesService.deleteClasses(classname);
    }
}
