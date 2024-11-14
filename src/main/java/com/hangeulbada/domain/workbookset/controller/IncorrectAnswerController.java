package com.hangeulbada.domain.workbookset.controller;

import com.hangeulbada.domain.workbookset.dto.TagCountDto;
import com.hangeulbada.domain.workbookset.service.IncorrectAnswerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/incorrect")
@RequiredArgsConstructor
@Tag(name= "Incorrect Answers",description = "오답 관리 API")
public class IncorrectAnswerController {
    private final IncorrectAnswerService incorrectAnswerService;

    @GetMapping("/statistics")
    @Operation(summary = "오답 통계", description = "오답 통계를 위한 태그 별 오답 수")
    public ResponseEntity<List<TagCountDto>> getIncorrectCountByTag(Principal principal){
        return ResponseEntity.ok(incorrectAnswerService.countIncorrects(principal.getName()));
    }

}