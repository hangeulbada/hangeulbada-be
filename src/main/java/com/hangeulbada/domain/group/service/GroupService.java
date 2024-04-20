package com.hangeulbada.domain.group.service;

import com.hangeulbada.domain.group.dto.GroupDTO;
import com.hangeulbada.domain.group.dto.GroupRequestDTO;

import java.util.List;

public interface GroupService{
    GroupDTO createGroup(GroupRequestDTO groupRequestDTO);
    List<GroupDTO> getAllGroup();
    void deleteGroup(String id);
}

