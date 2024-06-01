package com.hangeulbada.domain.group.service;

import com.hangeulbada.domain.assignment.entity.Assignment;
import com.hangeulbada.domain.assignment.repository.AssignmentRepository;
import com.hangeulbada.domain.auth.entity.User;
import com.hangeulbada.domain.auth.repository.UserRepository;
import com.hangeulbada.domain.group.dto.GroupAttendResponse;
import com.hangeulbada.domain.group.dto.GroupDTO;
import com.hangeulbada.domain.group.dto.GroupRequest;
import com.hangeulbada.domain.group.dto.SubmitDTO;
import com.hangeulbada.domain.group.entity.Group;
import com.hangeulbada.domain.group.repository.GroupRepository;
import com.hangeulbada.domain.workbookset.entity.Workbook;
import com.hangeulbada.domain.workbookset.repository.WorkbookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class GroupService{
    private final GroupRepository groupRepository;
    private final ModelMapper mapper;
//    private final AssignmentService assignmentService;
    private final AssignmentRepository assignmentRepository;
    private final WorkbookRepository workbookRepository;
    private final UserRepository userRepository;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";

    public boolean isValidRequest(String id, String groupId){
        log.info("id: "+id+" groupId: "+groupId);
        try{
            Optional<Group> group = groupRepository.findById(groupId);
            return group.map(value -> value.getTeacherId().equals(id)).orElse(false);

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
            return mapper.map(group, GroupAttendResponse.class);
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

    public List<GroupAttendResponse> getAttendGroup(String studentId){
        List<Group> groups = groupRepository.findByStudentIdsContaining(studentId);
        return groups.stream()
                .map(group -> mapper.map(group, GroupAttendResponse.class))
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
        Optional<Group> optionalGroup = groupRepository.findById(groupId);
        if (optionalGroup.isEmpty()) {
            throw new NoSuchElementException("No group found with the ID: " + groupId);
        }
        Group group = optionalGroup.get();
        List<Assignment> assignments = assignmentRepository.findByStudentIdIn(group.getStudentIds());

        assignments.sort(Comparator.comparing(Assignment::getSubmitDate));
        log.info("assignments: "+assignments.size());

        return assignments.stream().map(assignment -> {
            Optional<Workbook> optionalWorkbook = workbookRepository.findById(assignment.getWorkbookId());
            if (optionalWorkbook.isEmpty()) {
                log.warn("No workbook found with the ID: {}", assignment.getWorkbookId());
                return null;
            }
            Workbook workbook = optionalWorkbook.get();

            Optional<User> optionalUser = userRepository.findById(assignment.getStudentId());
            if (optionalUser.isEmpty()) {
                log.warn("No user found with the student ID: {}", assignment.getStudentId());
                return null;

            }
            User user = optionalUser.get();

            return SubmitDTO.builder()
                    .submitDate(assignment.getSubmitDate().toString().split("T")[0])
                    .score(assignment.getScore())
                    .assignmentId(assignment.getId())
                    .workbookId(workbook.getId())
                    .workbookTitle(workbook.getTitle())
                    .name(user.getName())
                    .studentId(user.getId())
                    .build();

        }).collect(Collectors.toList());
    }
}
