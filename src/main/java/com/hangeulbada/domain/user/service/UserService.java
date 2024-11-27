package com.hangeulbada.domain.user.service;

import com.hangeulbada.domain.assignment.repository.AssignmentRepository;
import com.hangeulbada.domain.auth.dto.UserDTO;
import com.hangeulbada.domain.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
    public UserDTO getUserById(String id){
        return mapper.map(UserRepository.findById(id).orElse(null), UserDTO.class);
    }
}
