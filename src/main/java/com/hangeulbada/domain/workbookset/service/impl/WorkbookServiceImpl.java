package com.hangeulbada.domain.workbookset.service.impl;

import com.hangeulbada.domain.workbookset.dto.WorkbookDto;
import com.hangeulbada.domain.workbookset.entity.Workbook;
import com.hangeulbada.domain.workbookset.exception.ResourceNotFoundException;
import com.hangeulbada.domain.workbookset.repository.WorkbookRepository;
import com.hangeulbada.domain.workbookset.service.WorkbookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkbookServiceImpl implements WorkbookService {

    private final WorkbookRepository workbookRepository;
    private final ModelMapper mapper;


    @Override
    public WorkbookDto createWorkbook(WorkbookDto workbookDto) {
        Workbook workbook = mapper.map(workbookDto, Workbook.class);
        log.info("workbook: {}", workbook);
        Workbook newWorkbook = workbookRepository.save(workbook);
        return mapper.map(newWorkbook, WorkbookDto.class);
    }

    @Override
    public List<WorkbookDto> getWorkbookList() {
        List<Workbook> workbookList = workbookRepository.findAll();
        return workbookList.stream().map(workbook -> mapper.map(workbook, WorkbookDto.class)).collect(Collectors.toList());
    }

    @Override
    public WorkbookDto getWorkbookById(String workbookId) {
        Workbook workbook = workbookRepository.findById(workbookId)
                .orElseThrow(()-> new ResourceNotFoundException("Workbook","id", workbookId));
        return mapper.map(workbook, WorkbookDto.class);
    }

    @Override
    public void deleteWorkbook(String workbookId) {
        Workbook workbook = workbookRepository.findById(workbookId)
                .orElseThrow(()-> new ResourceNotFoundException("Workbook","id", workbookId));
        workbookRepository.deleteById(workbookId);
    }
}
