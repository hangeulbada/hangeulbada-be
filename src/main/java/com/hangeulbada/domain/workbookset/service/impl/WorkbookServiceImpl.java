package com.hangeulbada.domain.workbookset.service.impl;

import com.hangeulbada.domain.group.entity.Group;
import com.hangeulbada.domain.group.repository.GroupRepository;
import com.hangeulbada.domain.workbookset.dto.WorkbookDto;
import com.hangeulbada.domain.workbookset.dto.WorkbookRequestDTO;
import com.hangeulbada.domain.workbookset.entity.Workbook;
import com.hangeulbada.domain.workbookset.exception.NotAuthorizedException;
import com.hangeulbada.domain.workbookset.exception.ResourceNotFoundException;
import com.hangeulbada.domain.workbookset.repository.WorkbookRepository;
import com.hangeulbada.domain.workbookset.service.WorkbookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkbookServiceImpl implements WorkbookService {

    private final WorkbookRepository workbookRepository;
    private final GroupRepository groupRepository;
    private final ModelMapper mapper;


    @Override
    public WorkbookDto createWorkbook(String teacherId, WorkbookRequestDTO workbookDto) {

        Workbook workbook = mapper.map(workbookDto, Workbook.class);
        workbook.setTeacherId(teacherId);

        log.info("workbook: {}", workbook);
        workbookRepository.save(workbook);

        return mapper.map(workbook, WorkbookDto.class);
    }

    @Override
    public List<WorkbookDto> getWorkbookList(String teacherId) {
        List<Workbook> workbookList = workbookRepository.findByTeacherId(teacherId);
        return workbookList.stream().map(workbook -> mapper.map(workbook, WorkbookDto.class)).collect(Collectors.toList());
    }

    @Override
    public WorkbookDto getWorkbookById(String workbookId) {
        Workbook workbook = workbookRepository.findById(workbookId)
                .orElseThrow(()-> new ResourceNotFoundException("Workbook","id", workbookId));
        return mapper.map(workbook, WorkbookDto.class);
    }

    @Override
    public void deleteWorkbook(String teacherId, String workbookId) {
        Workbook workbook = workbookRepository.findById(workbookId)
                .orElseThrow(()-> new ResourceNotFoundException("Workbook","id", workbookId));
        if (!workbook.getTeacherId().equals(teacherId))
            throw new NotAuthorizedException("작성자만 삭제할 수 있습니다.");
        workbookRepository.deleteById(workbookId);
    }


    @Override
    public List<WorkbookDto> getGroupWorkbooks(String groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new IllegalArgumentException("Invalid group ID"));
        if(group.getWorkbookIds()==null) group.setWorkbookIds(new ArrayList<>());
        List<String> workbookIds = group.getWorkbookIds();
        return workbookRepository.findAllById(workbookIds)
                .stream()
                .map(workbook -> mapper.map(workbook, WorkbookDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public WorkbookDto createGroupWorkbook(String teacherId, String groupId, WorkbookRequestDTO workbookDto) {
        Workbook workbook = mapper.map(workbookDto, Workbook.class);
        workbook.setTeacherId(teacherId);
        workbook = workbookRepository.save(workbook);
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new IllegalArgumentException("Invalid group ID"));
        if(group.getWorkbookIds()==null) group.setWorkbookIds(new ArrayList<>());
        group.getWorkbookIds().add(workbook.getId());
        groupRepository.save(group);
        return mapper.map(workbook, WorkbookDto.class);
    }

    @Override
    public void addGroupWorkbook(String teacherId, String groupId, String workbookId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new IllegalArgumentException("Invalid group ID"));
        if(group.getWorkbookIds()==null) group.setWorkbookIds(new ArrayList<>());
        group.getWorkbookIds().add(workbookId);
        groupRepository.save(group);
    }

    @Override
    public void deleteGroupWorkbook(String teacherId, String groupId, String workbookId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new IllegalArgumentException("Invalid group ID"));
        if(group.getWorkbookIds()==null) group.setWorkbookIds(new ArrayList<>());
        group.getWorkbookIds().remove(workbookId);
        groupRepository.save(group);
    }
}
