package com.hangeulbada.domain.user.service;

import com.hangeulbada.domain.assignment.repository.AssignmentRepository;
import com.hangeulbada.domain.auth.dto.UserDTO;
import com.hangeulbada.domain.auth.repository.UserRepository;
import com.hangeulbada.domain.group.dto.GroupAssignmentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository UserRepository;
    private final AssignmentRepository assignmentRepository;
    private final ModelMapper mapper;
    public void deleteUser(String id){
        UserRepository.deleteById(id);
    }
    public List<GroupAssignmentDTO> getGroupAssignment(String groupId, String studentId){
        return assignmentRepository.findGroupAssignmentsByStudentId(studentId);
    }
    public UserDTO getUserById(String id){
        return mapper.map(UserRepository.findById(id).orElse(null), UserDTO.class);
    }
}
