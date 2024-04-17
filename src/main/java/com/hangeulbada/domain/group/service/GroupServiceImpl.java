package com.hangeulbada.domain.group.service;

import com.hangeulbada.domain.group.dto.GroupCreateRequestDto;
import com.hangeulbada.domain.group.dto.GroupCreateResponseDto;
import com.hangeulbada.domain.group.dto.GroupResponseDto;
import com.hangeulbada.domain.group.repository.Group;
import com.hangeulbada.domain.group.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class GroupServiceImpl implements GroupService{
    private final GroupRepository groupRepository;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";

    private String generateGroupCode(){
        StringBuilder codeBuilder = new StringBuilder();
        Random random = new Random();
        for(int i=0;i<6;i++){
            char randomChar = CHARACTERS.charAt(random.nextInt(CHARACTERS.length()));
            codeBuilder.append(randomChar);
        }

        return codeBuilder.toString();
    }
    @Override
//    @Transactional
    public GroupCreateResponseDto createGroup(User user, GroupCreateRequestDto groupCreateRequestDto) {
        String groupCode = generateGroupCode();
        Group group = groupCreateRequestDto.toEntity(user);
        group.setGroupCode(groupCode);
        groupRepository.save(group);
        GroupCreateResponseDto responseDto = GroupCreateResponseDto.builder()
                .groupCode(groupCode)
                .createdAt(group.getCreatedAt())
                .updatedAt(group.getUpdatedAt())
                .build();
        return responseDto;
    }


    @Override
    @Transactional
    public List<GroupResponseDto> getGroup() {
        List<GroupResponseDto> group = groupRepository.findAll()
                .stream().map(GroupResponseDto::from).toList();
        return group;
    }
    @Override
    @Transactional
    public void deleteGroup(String id){
        groupRepository.deleteById(id);
    }
}
