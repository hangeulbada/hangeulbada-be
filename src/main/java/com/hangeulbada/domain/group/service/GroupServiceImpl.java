package com.hangeulbada.domain.group.service;

import com.hangeulbada.domain.assignment.service.AssignmentService;
import com.hangeulbada.domain.group.dto.GroupCreateRequestDto;
import com.hangeulbada.domain.group.dto.Group;
import com.hangeulbada.domain.group.dto.SubmitDTO;
import com.hangeulbada.domain.group.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class GroupServiceImpl implements GroupService{
    private final GroupRepository groupRepository;
    private final ModelMapper mapper;
    private final AssignmentService assignmentService;
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
    public Group createGroup(String id, GroupCreateRequestDto groupRequestDTO) {
        String groupCode = generateGroupCode();
        Group group = Group.builder()
                .groupName(groupRequestDTO.getGroupName())
                .teacherId(id)
                .groupCode(groupCode)
                .build();

        groupRepository.save(mapper.map(group, com.hangeulbada.domain.group.entity.Group.class));

        return mapper.map(group, Group.class);
    }

    @Override
    @Transactional
    public Group getGroup(String id) {
        com.hangeulbada.domain.group.entity.Group group = groupRepository.findById(id).orElseThrow();
        return mapper.map(group, Group.class);
    }

    @Override
    @Transactional
    public List<Group> getAllGroup() {
        List<com.hangeulbada.domain.group.entity.Group> groups = groupRepository.findAll();
        return groups.stream()
                .map(group -> mapper.map(group, Group.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<Group> getAllGroupById(String id){
        List<com.hangeulbada.domain.group.entity.Group> groups = groupRepository.findByTeacherId(id);
        return groups.stream()
                .map(group -> mapper.map(group, Group.class))
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public void deleteGroup(String id){
        groupRepository.deleteById(id);
    }

    @Override
    public List<SubmitDTO> getRecentSubmit(String groupId){
        return groupRepository.getRecentSubmit(groupId);
    }
}
