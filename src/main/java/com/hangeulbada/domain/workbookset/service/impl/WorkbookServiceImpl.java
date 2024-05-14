package com.hangeulbada.domain.workbookset.service.impl;

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
import org.springframework.http.HttpStatus;
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
}
