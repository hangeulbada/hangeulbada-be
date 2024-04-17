package com.hangeulbada.domain.group.service;

import com.hangeulbada.domain.group.dto.GroupCreateRequestDto;
import com.hangeulbada.domain.group.dto.GroupDTO;
import org.apache.catalina.User;

import java.util.List;

public interface GroupService{
    GroupDTO createGroup(User user, GroupCreateRequestDto groupCreateRequestDto);
    List<GroupDTO> getAllGroup();
    void deleteGroup(String id);
}

