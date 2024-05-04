package com.hangeulbada.domain.group.service;

import com.hangeulbada.domain.assignment.service.AssignmentService;
import com.hangeulbada.domain.group.dto.GroupDTO;
import com.hangeulbada.domain.group.dto.SubmitDTO;
import com.hangeulbada.domain.group.entity.Group;
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

    @Override
    public boolean isValidRequest(String id, String groupId){
        Group group = groupRepository.findById(groupId).orElseThrow();
        return group.getTeacherId().equals(id);
    }

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
    @Transactional
    public GroupDTO createGroup(String id, String groupName) {
        GroupDTO group = GroupDTO.builder()
                .id(null)
                .groupName(groupName)
                .teacherId(id)
                .groupCode(generateGroupCode())
                .build();
        Group newGroup = mapper.map(group, Group.class);
        groupRepository.save(newGroup);
        return mapper.map(newGroup, GroupDTO.class);
    }


    @Override
    @Transactional
    public GroupDTO getGroup(String id) {
        Group group = groupRepository.findById(id).orElseThrow();
        return mapper.map(group, GroupDTO.class);
    }

    @Override
    @Transactional
    public List<GroupDTO> getAllGroup() {
        List<Group> groups = groupRepository.findAll();
        return groups.stream()
                .map(group -> mapper.map(group, GroupDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<GroupDTO> getAllGroupById(String id){
        List<Group> groups = groupRepository.findByTeacherId(id);
        return groups.stream()
                .map(group -> mapper.map(group, GroupDTO.class))
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
