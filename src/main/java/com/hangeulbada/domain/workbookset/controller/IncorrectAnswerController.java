package com.hangeulbada.domain.workbookset.controller;

import com.hangeulbada.domain.workbookset.dto.TagCountDto;
import com.hangeulbada.domain.workbookset.dto.TagRequestDto;
import com.hangeulbada.domain.workbookset.dto.WorkbookIdResponseDto;
import com.hangeulbada.domain.workbookset.service.IncorrectAnswerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/get-incorrects")
    @Operation(summary = "오답 문제들", description = "태그로 오답 문제 가져오기")
    public ResponseEntity<List<String>> getIncorrectQids(@Valid @RequestBody TagRequestDto tagRequestDto, Principal principal){
        return ResponseEntity.ok(incorrectAnswerService.getIncorrectQids(principal.getName(),tagRequestDto));
    }

    @PostMapping("/review")
    @Operation(summary = "오답 문제집 생성", description = "원하는 종류의 오답 문제집 생성")
    public ResponseEntity<WorkbookIdResponseDto> createIncorrectsWorkbook(@Valid @RequestBody TagRequestDto tagRequestDto, Principal principal){
        return ResponseEntity.ok(incorrectAnswerService.createIncorrectsWorkbook(principal.getName(),tagRequestDto));
    }
}