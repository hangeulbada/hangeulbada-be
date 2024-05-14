package com.hangeulbada.domain.group.service;

import com.hangeulbada.domain.group.dto.GroupAttendResponse;
import com.hangeulbada.domain.group.dto.GroupDTO;
import com.hangeulbada.domain.group.dto.GroupRequest;
import com.hangeulbada.domain.group.dto.SubmitDTO;
import com.hangeulbada.domain.group.entity.Group;
import com.hangeulbada.domain.group.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class GroupService{
    private final GroupRepository groupRepository;
    private final ModelMapper mapper;
//    private final AssignmentService assignmentService;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";

    public boolean isValidRequest(String id, String groupId){
        log.info("id: "+id+" groupId: "+groupId);
        try{
            Optional<Group> group = groupRepository.findById(groupId);
            if (group.isEmpty()){
                return false;
            }

            log.info("group: "+group.get().getTeacherId());
            return group.get().getTeacherId().equals(id);
        }
        catch (Exception e){
            return false;
        }
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
    @Transactional
    public GroupDTO createGroup(String id, GroupRequest request) {
        GroupDTO group = GroupDTO.builder()
                .id(null)
                .groupName(request.getGroupName())
                .description(request.getDescription())
                .teacherId(id)
                .groupCode(generateGroupCode())
                .build();
        Group newGroup = mapper.map(group, Group.class);
        groupRepository.save(newGroup);
        return mapper.map(newGroup, GroupDTO.class);
    }


    @Transactional
    public GroupDTO getGroup(String id) {
        Group group = groupRepository.findById(id).orElseThrow();
        return mapper.map(group, GroupDTO.class);
    }

    public GroupAttendResponse attendGroup(String code, String studentId) {
        log.info("code: "+code+" studentId: "+studentId);
        Optional<Group> tmpGroup = groupRepository.findByGroupCode(code);
        log.info("group: "+tmpGroup);
        if(tmpGroup.isPresent()){
            Group group = tmpGroup.get();
            if(group.getStudentIds() == null){
                group.setStudentIds(new ArrayList<>());
            }
            if(group.getStudentIds().contains(studentId)){
                throw new IllegalArgumentException("이미 그룹에 참여하고 있습니다.");
            }
            group.getStudentIds().add(studentId);
            groupRepository.save(group);
            GroupDTO groupDTO = mapper.map(group, GroupDTO.class);
            return GroupAttendResponse.builder()
                    .id(groupDTO.getId())
                    .groupName(groupDTO.getGroupName())
                    .description(groupDTO.getDescription())
                    .teacherId(groupDTO.getTeacherId())
                    .groupCode(groupDTO.getGroupCode())
                    .build();
        }
        return null;
    }

    public void leaveGroup(String groupId, String studentId){
        Optional<Group> tmpGroup = groupRepository.findById(groupId);
        if(tmpGroup.isPresent()){
            Group group = tmpGroup.get();
            if(group.getStudentIds().contains(studentId)){
                group.getStudentIds().remove(studentId);
                groupRepository.save(group);
            }
        }
    }

    public List<GroupDTO> getAttendGroup(String studentId){
        List<Group> groups = groupRepository.findByStudentIdsContaining(studentId);
        return groups.stream()
                .map(group -> mapper.map(group, GroupDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<GroupDTO> getAllGroupById(String id){
        List<Group> groups = groupRepository.findByTeacherId(id);
        return groups.stream()
                .map(group -> mapper.map(group, GroupDTO.class))
                .collect(Collectors.toList());
    }


    @Transactional
    public void deleteGroup(String id){
        groupRepository.deleteById(id);
    }

    public List<SubmitDTO> getRecentSubmit(String groupId){
        return groupRepository.getRecentSubmit(groupId);
    }



}
