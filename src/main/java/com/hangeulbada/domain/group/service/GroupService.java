package com.hangeulbada.domain.group.service;

import com.hangeulbada.domain.group.dto.GroupAttendResponse;
import com.hangeulbada.domain.group.dto.GroupDTO;
import com.hangeulbada.domain.group.dto.GroupRequest;
import com.hangeulbada.domain.group.dto.SubmitDTO;

import java.util.List;

public interface GroupService{
    GroupDTO createGroup(String id, GroupRequest request);
    GroupDTO getGroup(String id);
    GroupAttendResponse attendGroup(String code, String studentId);
    List<GroupDTO> getAllGroupById(String id);
    void deleteGroup(String id);
    List<SubmitDTO> getRecentSubmit(String id);
    boolean isValidRequest(String id, String groupId);
}

