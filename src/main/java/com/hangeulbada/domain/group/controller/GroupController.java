package com.hangeulbada.domain.group.controller;

import com.hangeulbada.domain.group.dto.GroupDTO;
import com.hangeulbada.domain.group.dto.GroupRequestDTO;
import com.hangeulbada.domain.group.service.GroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/group")
public class GroupController {
    private final GroupService groupService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<GroupDTO> createGroup(@RequestBody GroupRequestDTO groupRequest)
    {
        log.info("Request: {}", groupRequest.toString());
        GroupDTO group = groupService.createGroup(groupRequest);
        log.info("Group created: {}", group.toString());
        return ResponseEntity.ok(group);
    }
    @GetMapping("/all")
    ResponseEntity<List<GroupDTO>> getAllGroup(){
        List<GroupDTO> groupList = groupService.getAllGroup();
        return ResponseEntity.ok(groupList);
    }
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteGroup(@PathVariable String id){
        groupService.deleteGroup(id);
    }
}
