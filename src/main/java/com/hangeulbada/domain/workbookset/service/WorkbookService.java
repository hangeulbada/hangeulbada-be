package com.hangeulbada.domain.workbookset.service;

import com.hangeulbada.domain.workbookset.dto.WorkbookDto;

import java.util.List;

public interface WorkbookService {
    WorkbookDto createWorkbook(WorkbookDto workbookDto);
    List<WorkbookDto> getWorkbookList(); // 추후 pagination 추가
    WorkbookDto getWorkbookById(long workbookId);
    void deleteWorkbook(long workbookId);

}
