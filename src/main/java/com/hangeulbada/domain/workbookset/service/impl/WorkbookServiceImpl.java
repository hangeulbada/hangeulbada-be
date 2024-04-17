package com.hangeulbada.domain.workbookset.service.impl;

import com.hangeulbada.domain.workbookset.entity.Workbook;
import com.hangeulbada.domain.workbookset.exception.ResourceNotFoundException;
import com.hangeulbada.domain.workbookset.dto.WorkbookDto;
import com.hangeulbada.domain.workbookset.repository.WorkbookRepository;
import com.hangeulbada.domain.workbookset.service.WorkbookService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkbookServiceImpl implements WorkbookService {

    private WorkbookRepository workbookRepository;
    private ModelMapper mapper;

    public WorkbookServiceImpl(WorkbookRepository workbookRepository, ModelMapper mapper) {
        this.workbookRepository = workbookRepository;
        this.mapper = mapper;
    }

    @Override
    public WorkbookDto createWorkbook(WorkbookDto workbookDto) {
        Workbook workbook = mapper.map(workbookDto, Workbook.class);

        workbook.setActivated(true);
        workbook.setCreatedAt(LocalDateTime.now());
        Workbook newWorkbook = workbookRepository.save(workbook);
        return mapper.map(newWorkbook, WorkbookDto.class);
    }

    @Override
    public List<WorkbookDto> getWorkbookList() {
        List<Workbook> workbookList = workbookRepository.findAll();
        return workbookList.stream().map(workbook -> mapper.map(workbook, WorkbookDto.class)).collect(Collectors.toList());
    }

    @Override
    public WorkbookDto getWorkbookById(long workbookId) {
        Workbook workbook = workbookRepository.findById(workbookId)
                .orElseThrow(()-> new ResourceNotFoundException("Workbook","id", workbookId));
        return mapper.map(workbook, WorkbookDto.class);
    }

    @Override
    public void deleteWorkbook(long workbookId) {
        Workbook workbook = workbookRepository.findById(workbookId)
                .orElseThrow(()-> new ResourceNotFoundException("Workbook","id", workbookId));
        workbookRepository.deleteById(workbookId);
    }
}
