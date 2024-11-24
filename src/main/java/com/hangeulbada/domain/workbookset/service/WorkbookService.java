package com.hangeulbada.domain.workbookset.service;

import com.hangeulbada.domain.workbookset.dto.WorkbookAddRequest;
import com.hangeulbada.domain.workbookset.dto.WorkbookDto;
import com.hangeulbada.domain.workbookset.dto.WorkbookRequestDTO;
import com.hangeulbada.domain.workbookset.dto.WorkbookIdResponseDto;

import java.util.List;

public interface WorkbookService {
    void updateWorkbookDifficulty(String workbookId);
    double calculateWorkbookDifficulty(String workbookId);
    List<String> getQuestionIdsByWorkbookId(String workbookId);
    WorkbookDto createWorkbook(String teacherId, WorkbookRequestDTO workbookDto);
    List<WorkbookDto> getWorkbookList(String teacherId); // 추후 pagination 추가
    WorkbookDto getWorkbookById(String workbookId);
    void deleteWorkbook(String teacherId, String workbookId);
    WorkbookIdResponseDto addWorkbook(WorkbookAddRequest workbookAddRequest,String teacherId);

    List<WorkbookDto> getGroupWorkbooks(String groupId);
    WorkbookDto createGroupWorkbook(String teacherId, String groupId, WorkbookRequestDTO workbookDto);
    void addGroupWorkbook(String teacherId, String groupId, String workbookId);
    void deleteGroupWorkbook(String teacherId, String groupId, String workbookId);
}
