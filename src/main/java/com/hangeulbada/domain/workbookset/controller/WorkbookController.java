package com.hangeulbada.domain.workbookset.controller;

import com.hangeulbada.domain.workbookset.dto.WorkbookDto;
import com.hangeulbada.domain.workbookset.service.WorkbookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workbook")
public class WorkbookController {
    private WorkbookService workbookService;

    public WorkbookController(WorkbookService workbookService) {
        this.workbookService = workbookService;
    }

    @PostMapping
    public ResponseEntity<WorkbookDto> createWorkbook(@Valid @RequestBody WorkbookDto workbookDto){
        return new ResponseEntity<>(workbookService.createWorkbook(workbookDto), HttpStatus.CREATED);
    }

    @GetMapping("/{workbookId}")
    public ResponseEntity<WorkbookDto> getWorkbookById(@PathVariable(name="workbookId") long id){
        return ResponseEntity.ok(workbookService.getWorkbookById(id));
    }

    @GetMapping
    public ResponseEntity<List<WorkbookDto>> getAllWorkbooks(){
        return ResponseEntity.ok(workbookService.getWorkbookList());
    }

    @DeleteMapping("/{workbookId}")
    public ResponseEntity<String> deleteWorkbookById(@PathVariable(name="workbookId") long id){
        workbookService.deleteWorkbook(id);
        return new ResponseEntity<>("정상적으로 세트가 삭제되었습니다.",HttpStatus.OK);
    }
}
