package com.hangeulbada.domain.assignment.controller;

import com.hangeulbada.domain.annotation.StudentTag;
import com.hangeulbada.domain.assignment.dto.AssignmentSummaryDto;
import com.hangeulbada.domain.assignment.dto.ScoreDTO;
import com.hangeulbada.domain.assignment.dto.SpecificAssignmentDTO;
import com.hangeulbada.domain.assignment.service.AssignmentService;
import com.hangeulbada.domain.group.dto.GroupAssignmentDTO;
import com.hangeulbada.domain.ocr.dto.OCRRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AssignmentController {
    private final AssignmentService assignmentService;

    @StudentTag
    @PostMapping("/assignment/submit")
    @Operation(summary="OCR 요청", description="OCR 요청 전송")
    @ApiResponse(responseCode = "200", description = "OCR 후 문항별 점수 반환")
    public ResponseEntity<List<ScoreDTO>> submit(
            @RequestBody @Parameter(description = "OCR 요청 데이터", required = true, content = @Content(schema = @Schema(implementation = OCRRequest.class)))
            OCRRequest ocrRequest,
            Principal principal
    ) {
        return ResponseEntity.ok(assignmentService.requestOCR(ocrRequest, principal.getName()));
    }

    @StudentTag
    @GetMapping("/assignment/{workbookId}/{studentId}")
    @Operation(summary="문제집 답안 조회", description="문제집의 학생 답안을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "문제집 답안 조회 성공")
    public ResponseEntity<SpecificAssignmentDTO> getAssignment(
            @PathVariable("workbookId") @Parameter(description = "문제집 ID", required = true) String workbookId,
            @PathVariable("studentId") @Parameter(description = "학생 ID", required = true) String studentId
    ) {
        SpecificAssignmentDTO assignmentDTO = assignmentService.getAssignment(studentId, workbookId);
        return ResponseEntity.ok(assignmentDTO);
    }

    @StudentTag
    @GetMapping("/student/assignment")
    @Operation(summary = "학생이 푼 문제집", description = "학생이 푼 모든 문제집을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "클래스 문제집 조회 성공", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = GroupAssignmentDTO.class)))
    public ResponseEntity<List<GroupAssignmentDTO>> getAssignment(Principal principal){
        List<GroupAssignmentDTO> assignment = assignmentService.getUserAssignments(principal.getName());
        return ResponseEntity.ok(assignment);
    }

    @StudentTag
    @GetMapping("/student/workbook/{workbookId}/assignment")
    @Operation(summary = "학생(사용자)이 특정 문제집에 낸 답안", description = "유저의 해당 workbook 모든 답안들을 가져옵니다")
    @ApiResponse(responseCode = "200", description = "클래스 문제집 조회 성공")
    public ResponseEntity<List<AssignmentSummaryDto>> getAssignment(@PathVariable(value = "workbookId")String workbookId, Principal principal){
        List<AssignmentSummaryDto> assignment = assignmentService.getUserAssignmentsSummary(workbookId, principal.getName());
        return ResponseEntity.ok(assignment);
    }
    @StudentTag
    @GetMapping("/student/assignment/{assignmentId}")
    @Operation(summary = "답안 확인", description = "답안을 확인합니다")
    @ApiResponse(responseCode = "200", description = "클래스 문제집 조회 성공")
    public ResponseEntity<SpecificAssignmentDTO> getSpecificAssignment(@PathVariable(value = "assignmentId")String assignmentId, Principal principal){
        return ResponseEntity.ok(assignmentService.getAssignmentById(assignmentId, principal.getName()));
    }

}