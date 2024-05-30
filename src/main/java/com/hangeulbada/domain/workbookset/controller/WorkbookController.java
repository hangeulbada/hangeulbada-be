package com.hangeulbada.domain.workbookset.controller;

import com.hangeulbada.domain.annotation.StudentTag;
import com.hangeulbada.domain.workbookset.dto.ExceptionResponse;
import com.hangeulbada.domain.workbookset.dto.WorkbookDto;
import com.hangeulbada.domain.workbookset.dto.WorkbookRequestDTO;
import com.hangeulbada.domain.workbookset.dto.WorkbooksDTO;
import com.hangeulbada.domain.workbookset.service.WorkbookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/workbook")
@RequiredArgsConstructor
@Tag(name = "Workbook", description = "문제집 관리 API")
public class WorkbookController {
    private final WorkbookService workbookService;

    @PostMapping
    @Operation(summary = "세트 생성", description = "세트를 생성합니다.")
    @ApiResponse(responseCode = "201", description = "생성 성공")
    public ResponseEntity<WorkbookDto> createWorkbook(@Valid @RequestBody WorkbookRequestDTO workbookDto, Principal principal){
        log.info("workbookDto: {}", workbookDto);
        return new ResponseEntity<>(workbookService.createWorkbook(principal.getName(), workbookDto), HttpStatus.CREATED);
    }

    @GetMapping("/{workbookId}")
    @Operation(summary = "세트 조회", description = "workbookId로 세트를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @ApiResponse(responseCode = "404", description = "존재하지 않는 객체입니다. (Exception 발생)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    public ResponseEntity<WorkbookDto> getWorkbookById(@PathVariable(name="workbookId") String id){
        return ResponseEntity.ok(workbookService.getWorkbookById(id));
    }

    @GetMapping
    @Operation(summary = "세트 조회", description = "현재 로그인한 유저가 작성한 모든 세트를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    public ResponseEntity<List<WorkbookDto>> getAllWorkbooks(Principal principal){
        return ResponseEntity.ok(workbookService.getWorkbookList(principal.getName()));
    }

    @DeleteMapping("/{workbookId}")
    @Operation(summary = "세트 삭제", description = "workbookId로 세트를 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "삭제 성공")
    @ApiResponse(responseCode = "401", description = "작성자만 수정할 수 있습니다. (Exception 발생)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "존재하지 않는 객체입니다. (Exception 발생)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    public ResponseEntity<String> deleteWorkbookById(@PathVariable(name="workbookId") String id, Principal principal){
        workbookService.deleteWorkbook(principal.getName(), id);
        return new ResponseEntity<>("정상적으로 세트가 삭제되었습니다.",HttpStatus.OK);
    }

    @StudentTag
    @GetMapping("/group/{groupId}/workbook")
    @Operation(summary = "클래스에 있는 문제집 조회", description = "클래스에 있는 문제집을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @ApiResponse(responseCode = "404", description = "존재하지 않는 객체입니다. (Exception 발생)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    public ResponseEntity<List<WorkbookDto>> getGroupWorkbooks(@PathVariable(name="groupId") String groupId){
        return ResponseEntity.ok(workbookService.getGroupWorkbooks(groupId));
    }

    @PostMapping("/group/{groupId}/workbook")
    @Operation(summary = "클래스 문제집 생성", description = "클래스에 문제집을 생성합니다.")
    @ApiResponse(responseCode = "201", description = "생성 성공")
    public ResponseEntity<WorkbookDto> createGroupWorkbook(@PathVariable(name="groupId") String groupId, @Valid @RequestBody WorkbookRequestDTO workbookDto, Principal principal){
        return new ResponseEntity<>(workbookService.createGroupWorkbook(principal.getName(), groupId, workbookDto), HttpStatus.CREATED);
    }

    @PostMapping("/group/{groupId}/workbooks")
    @Operation(summary = "클래스 문제집 추가", description = "클래스에 기존 문제집을 추가합니다.")
    @ApiResponse(responseCode = "200", description = "추가 성공")
    @ApiResponse(responseCode = "401", description = "작성자만 수정할 수 있습니다. (Exception 발생)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "존재하지 않는 객체입니다. (Exception 발생)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    public ResponseEntity<String> addGroupWorkbook(@PathVariable(name="groupId") String groupId, @RequestBody WorkbooksDTO workbookIds, Principal principal){
        for (String workbookId : workbookIds.getWorkbookIds()){
            workbookService.addGroupWorkbook(principal.getName(), groupId, workbookId);
        }
        return new ResponseEntity<>("정상적으로 세트가 추가되었습니다.",HttpStatus.OK);
    }

    @DeleteMapping("/group/{groupId}/workbooks/{workbookId}")
    @Operation(summary = "클래스 문제집 삭제", description = "클래스에 문제집을 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "삭제 성공")
    @ApiResponse(responseCode = "401", description = "작성자만 수정할 수 있습니다. (Exception 발생)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "존재하지 않는 객체입니다. (Exception 발생)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    public ResponseEntity<String> deleteGroupWorkbook(@PathVariable(name="groupId") String groupId, @PathVariable(name="workbookId") String workbookId, Principal principal){
        workbookService.deleteGroupWorkbook(principal.getName(), groupId, workbookId);
        return new ResponseEntity<>("정상적으로 세트가 삭제되었습니다.",HttpStatus.OK);
    }


}
