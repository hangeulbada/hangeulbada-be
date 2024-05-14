package com.hangeulbada.domain.user.service;

import com.hangeulbada.domain.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository UserRepository;
    public void deleteUser(String id){
        UserRepository.deleteById(id);
    }
}
