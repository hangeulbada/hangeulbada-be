package com.hangeulbada.domain.group.service;

import com.hangeulbada.domain.group.dto.GroupCreateRequestDto;
import com.hangeulbada.domain.group.dto.Group;
import com.hangeulbada.domain.group.dto.SubmitDTO;

import java.util.List;

public interface GroupService{
    Group createGroup(String id, GroupCreateRequestDto groupRequestDTO);
    Group getGroup(String id);
    List<Group> getAllGroup();
    List<Group> getAllGroupById(String id);
    void deleteGroup(String id);
    List<SubmitDTO> getRecentSubmit(String id);
}

