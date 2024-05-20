package com.hangeulbada.domain.group.controller;

import com.hangeulbada.domain.group.dto.GroupDTO;
import com.hangeulbada.domain.group.dto.GroupRequest;
import com.hangeulbada.domain.group.dto.SubmitDTO;
import com.hangeulbada.domain.group.service.GroupService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Group Controller", description = "클래스 관리 API")
public class GroupController {
    private final GroupService groupService;

    @PostMapping("/group")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Custom-Header", value = "Description of custom header", required = true, dataType = "string", paramType = "header")
    })
    @Operation(summary = "클래스 생성", description = "클래스를 생성합니다.")
    public ResponseEntity<GroupDTO> createGroup(@RequestBody GroupRequest request, Principal principal){
        GroupDTO group = groupService.createGroup(principal.getName(), request.getGroupName());
        return ResponseEntity.ok(group);
    }

    @GetMapping("/group")
    @Operation(summary = "모든 클래스 조회", description = "모든 클래스를 조회합니다.")
    public ResponseEntity<?> getAllGroup(Principal principal){
        //user의 id를 받아서 그룹을 조회
        if (principal == null){
            log.error("principal is null");
            return ResponseEntity.ok(null);
        }

        String id = principal.getName();
        log.info("id" + id);
        List<GroupDTO> group = groupService.getAllGroupById(id);
        return ResponseEntity.ok(group);
    }

    @GetMapping("/group/{groupId}")
    @Operation(summary = "특정 클래스 조회", description = "특정 클래스를 조회합니다.")
    public ResponseEntity<GroupDTO> getGroup(@PathVariable String groupId){
        GroupDTO groupList = groupService.getGroup(groupId);
        return ResponseEntity.ok(groupList);
    }

    @DeleteMapping("/group/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "클래스 삭제", description = "클래스를 삭제합니다.")
    public void deleteGroup(@PathVariable String groupId){
        groupService.deleteGroup(groupId);
    }

    @GetMapping("/group/{groupId}/submit")
    @Operation(summary = "클래스의 학생들이 푼 문제집", description = "클래스의 학생들이 푼 문제집을 조회합니다.")
    public ResponseEntity<List<SubmitDTO>> getSubmit(@PathVariable String groupId){
        List<SubmitDTO> submitList = groupService.getRecentSubmit(groupId);
        return ResponseEntity.ok(submitList);
    }

}
