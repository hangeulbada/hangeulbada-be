package com.hangeulbada.domain.assignment.service;

import com.hangeulbada.domain.assignment.dto.*;
import com.hangeulbada.domain.assignment.entity.Assignment;
import com.hangeulbada.domain.assignment.entity.AssignmentContent;
import com.hangeulbada.domain.assignment.repository.AssignmentRepository;
import com.hangeulbada.domain.externalapi.service.ApiService;
import com.hangeulbada.domain.group.dto.GroupAssignmentDTO;
import com.hangeulbada.domain.ocr.dto.OCRRequest;
import com.hangeulbada.domain.user.service.UserService;
import com.hangeulbada.domain.workbookset.entity.IncorrectAnswerTag;
import com.hangeulbada.domain.workbookset.exception.ResourceNotFoundException;
import com.hangeulbada.domain.workbookset.repository.IncorrectAnswerTagRepository;
import com.hangeulbada.domain.workbookset.service.QuestionService;
import com.hangeulbada.domain.workbookset.service.WorkbookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AssignmentService {
    private final AssignmentRepository assignmentRepository;
    private final QuestionService questionService;
    private final WorkbookService workbookService;
    private final UserService userService;
    private final ApiService apiService;
    private final ModelMapper mapper;
    private final IncorrectAnswerTagRepository incorrectAnswerTagRepository;

    public SpecificAssignmentDTO getAssignment(String studentId, String workbookId) {
        Assignment assignment = assignmentRepository.findLatestByStudentIdAndWorkbookId(studentId, workbookId);
        SpecificAssignmentDTO specificAssignmentDTO = mapper.map(assignment, SpecificAssignmentDTO.class);
        specificAssignmentDTO.setStudentName((userService.getUserById(studentId).getName()));
        specificAssignmentDTO.setImgS3Url(assignment.getImgS3Url());
        specificAssignmentDTO.setScore(assignment.getScore());
        return specificAssignmentDTO;
    }


    public AssignmentSavedDto requestOCR(OCRRequest ocrRequest, String studentId){
        String wId = ocrRequest.getWorkbookId();
        List<String> questionIds = workbookService.getQuestionIdsByWorkbookId(wId);

        Map<Integer, String> qIdMap = new HashMap<>();
        Map<String, String> workbook = new HashMap<>();
        List<String> questions = new ArrayList<>();
        Integer i=1;
        for(String qId : questionIds){
            String qContent = questionService.getQuestionById(wId,qId).getContent();
            workbook.put(i.toString(), qContent);
            questions.add(qContent);
            qIdMap.put(i,qId);
            i++;
        }

        AssignmentScoreRequestDto scoreRequestDto =
                new AssignmentScoreRequestDto().builder()
                        .workbook(workbook)
                        .answer(ocrRequest.getImageName())
                        .build();
        // OCR 요청
        AssignmentScoreResponseDTO assignmentDto = apiService.postAssignmentScore(scoreRequestDto);

        if (assignmentDto == null) {
            log.error("postAssignmentScore returned null for request: {}", scoreRequestDto);
            throw new IllegalStateException("Failed to retrieve assignment score. API returned null.");
        }
        List<ScoreDTO> scoreDTOS = new ArrayList<>();
        int score=0;
        for(QuestionAnalysisDto qAnalysis: assignmentDto.getAnswers()){
            scoreDTOS.add(
                    ScoreDTO.builder().number(qAnalysis.getNum()).simillarity(qAnalysis.getSimillarity()).build()
            );
            if (qAnalysis.getSimillarity()==100) score++;
        }

        //Assignment 저장
        Assignment newAssignment = mapper.map(assignmentDto, Assignment.class);
        newAssignment.setStudentId(studentId);
        newAssignment.setWorkbookId(wId);
        newAssignment.setScore(score);
        newAssignment.setSubmitDate(LocalDateTime.now());
        newAssignment.setImgS3Url(ocrRequest.getImageName());

        int idx=0;
        for(AssignmentContent content: newAssignment.getAnswers()){
            content.setQuestionFull(questions.get(idx++));
        }
        Assignment savedAssignment = assignmentRepository.save(newAssignment);

        //오답 태그 저장
        saveIncorrectTag(assignmentDto, qIdMap, studentId);
        return AssignmentSavedDto.builder().id(savedAssignment.getId()).scoreDtoList(scoreDTOS).build();
    }


    @Async
    public void saveIncorrectTag(AssignmentScoreResponseDTO assignmentDto, Map<Integer, String> qIdMap, String studentId){
        // ocr 작업 후 오답의 문제, 태그 등 저장
        List<QuestionAnalysisDto> analysisDtos = assignmentDto.getAnswers();
        for(QuestionAnalysisDto dto: analysisDtos){
            if(dto.getSimillarity()<100){
                for(AnalysisDetailDto analysis: dto.getAnalysis()){
                    for(String pronounce : analysis.getPronounce()){
                        incorrectAnswerTagRepository.save(new IncorrectAnswerTag().builder()
                                        .studentId(studentId).tag(pronounce).questionId(qIdMap.get(dto.getNum()))
                                .build());
                    }
                }
            }
        }
    }

    public List<GroupAssignmentDTO> getUserAssignments(String studentId){
        return assignmentRepository.findGroupAssignmentsByStudentId(studentId);
    }

    public List<AssignmentSummaryDto> getUserAssignmentsSummary(String workbookId, String studentId){
        return assignmentRepository.getWorkbookAssignment(studentId, workbookId);
    }

    public SpecificAssignmentDTO getAssignmentById(String id, String studentId){
        Assignment assignment = assignmentRepository.findAssignmentById(id).orElseThrow(()-> new ResourceNotFoundException("Assignment","id", id));
        SpecificAssignmentDTO assignmentDTO = mapper.map(assignment, SpecificAssignmentDTO.class);
        assignmentDTO.setStudentName(userService.getUserById(studentId).getName());
        return assignmentDTO;
    }
}
