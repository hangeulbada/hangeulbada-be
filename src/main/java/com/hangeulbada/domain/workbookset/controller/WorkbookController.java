package com.hangeulbada.domain.workbookset.controller;

import com.hangeulbada.domain.workbookset.dto.WorkbookDto;
import com.hangeulbada.domain.workbookset.dto.WorkbookRequestDTO;
import com.hangeulbada.domain.workbookset.service.WorkbookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/workbook")
@RequiredArgsConstructor
public class WorkbookController {
    private final WorkbookService workbookService;

    @PostMapping
    public ResponseEntity<WorkbookDto> createWorkbook(@Valid @RequestBody WorkbookRequestDTO workbookDto){
        log.info("workbookDto: {}", workbookDto);
        return new ResponseEntity<>(workbookService.createWorkbook(workbookDto), HttpStatus.CREATED);
    }

    @GetMapping("/{workbookId}")
    public ResponseEntity<WorkbookDto> getWorkbookById(@PathVariable(name="workbookId") String id){
        return ResponseEntity.ok(workbookService.getWorkbookById(id));
    }

    @GetMapping
    public ResponseEntity<List<WorkbookDto>> getAllWorkbooks(){
        return ResponseEntity.ok(workbookService.getWorkbookList());
    }

    @DeleteMapping("/{workbookId}")
    public ResponseEntity<String> deleteWorkbookById(@PathVariable(name="workbookId") String id){
        workbookService.deleteWorkbook(id);
        return new ResponseEntity<>("정상적으로 세트가 삭제되었습니다.",HttpStatus.OK);
    }
}
