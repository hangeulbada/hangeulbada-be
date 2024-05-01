package com.hangeulbada.domain.group.service;

import com.hangeulbada.domain.group.dto.GroupDTO;
import com.hangeulbada.domain.group.dto.GroupRequestDTO;
import com.hangeulbada.domain.group.dto.SubmitDTO;

import java.util.List;

public interface GroupService{
    GroupDTO createGroup(GroupRequestDTO groupRequestDTO);
    GroupDTO getGroup(String id);
    List<GroupDTO> getAllGroup();
    void deleteGroup(String id);
    List<SubmitDTO> getRecentSubmit(String id);
}

