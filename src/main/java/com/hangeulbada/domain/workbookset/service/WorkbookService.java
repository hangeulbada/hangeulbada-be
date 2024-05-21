package com.hangeulbada.domain.workbookset.service;

import com.hangeulbada.domain.workbookset.dto.WorkbookDto;
import com.hangeulbada.domain.workbookset.dto.WorkbookRequestDTO;

import java.util.List;

public interface WorkbookService {
    WorkbookDto createWorkbook(String teacherId, WorkbookRequestDTO workbookDto);
    List<WorkbookDto> getWorkbookList(String teacherId); // 추후 pagination 추가
    WorkbookDto getWorkbookById(String workbookId);
    void deleteWorkbook(String teacherId, String workbookId);
}
