package com.hangeulbada.domain.group.service;

import com.hangeulbada.domain.group.dto.GroupDTO;
import com.hangeulbada.domain.group.dto.GroupRequestDTO;
import com.hangeulbada.domain.group.repository.Group;
import com.hangeulbada.domain.group.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GroupServiceImpl implements GroupService{
    private final GroupRepository groupRepository;
    private final ModelMapper mapper;
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
    public GroupDTO createGroup(GroupRequestDTO groupRequestDTO) {
        String groupCode = generateGroupCode();

        Group group = groupRequestDTO.toEntity();
        group.setGroupCode(groupCode);
        groupRepository.save(group);

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
    public void deleteGroup(String id){
        groupRepository.deleteById(id);
    }
}
