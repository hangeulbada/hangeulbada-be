package com.hangeulbada.domain.group.service;

import com.hangeulbada.domain.group.dto.GroupCreateRequestDto;
import com.hangeulbada.domain.group.dto.GroupCreateResponseDto;
import com.hangeulbada.domain.group.dto.GroupResponseDto;
import org.apache.catalina.User;

import java.util.List;

public interface GroupService{
    GroupCreateResponseDto createGroup(User user, GroupCreateRequestDto groupCreateRequestDto);
    List<GroupResponseDto> getGroup();
    void deleteGroup(String id);
}

