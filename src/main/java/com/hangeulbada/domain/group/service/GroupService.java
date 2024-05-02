package com.hangeulbada.domain.group.service;

import com.hangeulbada.domain.group.dto.GroupDTO;
import com.hangeulbada.domain.group.dto.SubmitDTO;

import java.util.List;

public interface GroupService{
    GroupDTO createGroup(String id, String groupName);
    GroupDTO getGroup(String id);
    List<GroupDTO> getAllGroup();
    List<GroupDTO> getAllGroupById(String id);
    void deleteGroup(String id);
    List<SubmitDTO> getRecentSubmit(String id);
}

