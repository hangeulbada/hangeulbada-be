package com.hangeulbada.domain.group.controller;

import com.hangeulbada.domain.annotation.LoggedInUser;
import com.hangeulbada.domain.group.dto.GroupCreateRequestDto;
import com.hangeulbada.domain.group.dto.GroupDTO;
import com.hangeulbada.domain.group.service.GroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/group")
public class GroupApiController {
    private final GroupService groupService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<GroupDTO> createGroup(@LoggedInUser User user,
                                      @Valid @RequestBody GroupCreateRequestDto groupRequest)
    {
        GroupDTO group = groupService.createGroup(user, groupRequest);
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
