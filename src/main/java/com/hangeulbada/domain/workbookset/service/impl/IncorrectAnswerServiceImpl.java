package com.hangeulbada.domain.workbookset.service.impl;

import com.hangeulbada.domain.group.dto.IncorrectsGroupDTO;
import com.hangeulbada.domain.group.entity.Group;
import com.hangeulbada.domain.group.repository.GroupRepository;
import com.hangeulbada.domain.group.service.GroupService;
import com.hangeulbada.domain.user.service.UserService;
import com.hangeulbada.domain.workbookset.dto.*;
import com.hangeulbada.domain.workbookset.entity.Workbook;
import com.hangeulbada.domain.workbookset.exception.NoIncorrectsException;
import com.hangeulbada.domain.workbookset.repository.IncorrectAnswerTagRepository;
import com.hangeulbada.domain.workbookset.dto.QuestionIdsDTO;
import com.hangeulbada.domain.workbookset.repository.WorkbookRepository;
import com.hangeulbada.domain.workbookset.service.IncorrectAnswerService;
import com.hangeulbada.domain.workbookset.service.WorkbookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class IncorrectAnswerServiceImpl implements IncorrectAnswerService {

    public final IncorrectAnswerTagRepository incorrectsRepository;
    public final WorkbookService workbookService;
    private final ModelMapper mapper;
    public static final List<String> tagNames = Collections.unmodifiableList(
            new ArrayList<>(Arrays.asList("구개음화", "연음화", "경음화", "유음화", "비음화", "음운규칙 없음", "겹받침 쓰기", "기식음화"))
    );
    private final UserService userService;
    private final WorkbookRepository workbookRepository;
    private final GroupService groupService;
    private final GroupRepository groupRepository;
    private final IncorrectAnswerTagRepository incorrectAnswerTagRepository;

    @Override
    public List<TagCountDto> countIncorrects(String studentId) {
        List<TagCountDto> tagCountDtos = new ArrayList<>();
        for(String tag:tagNames){
            tagCountDtos.add(
                    TagCountDto.builder().tag(tag)
                            .count(incorrectsRepository.countIncorrectAnswerTagsByStudentIdAndTag(studentId, tag))
                            .build()
            );
        }
        return tagCountDtos;
    }

    //for testing
    @Override
    public List<String> getIncorrectQids(String studentId, TagRequestDto tagRequestDto) {
        QuestionIdsDTO questionIdsDTO = incorrectsRepository.findQuestionIdsByStudentIdAndTag(studentId, tagRequestDto.getTagName());
        if (questionIdsDTO == null) {
            throw new NoIncorrectsException(tagRequestDto.getTagName(), studentId);
        }
        return questionIdsDTO.getQuestionIds();
    }


    @Override
    public QuestionIdsDTO getIncorrectsByTag(String studentId, String tagName) {
        QuestionIdsDTO questionIdsDTO = incorrectsRepository.findQuestionIdsByStudentIdAndTag(studentId, tagName);
        if (questionIdsDTO == null) {
            throw new NoIncorrectsException(tagName, studentId);
        }
        return questionIdsDTO;
    }

    @Transactional
    @Override
    public WorkbookIdResponseDto createIncorrectsWorkbook(String studentId, TagRequestDto tagRequestDto){
        // 오답 태그로 이뤄진 문장 가져오기
        List<String> questionIdList = getIncorrectsByTag(studentId, tagRequestDto.getTagName()).getQuestionIds();

        // 문제집 생성 후 문제들 등록, 저장
        Workbook workbook = Workbook.builder()
                .teacherId(studentId)
                .title((userService.getUserById(studentId).getName())+"의 오답노트")
                .description(tagRequestDto.getTagName()+" 종류로 이뤄진 오답 문제들입니다.")
                .questionNum(questionIdList.size())
                .questionIds(questionIdList)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(7)) //기한 일주일 뒤까지
                .build();
        Workbook newWorkbook = workbookRepository.save(workbook);
        workbookService.updateWorkbookDifficulty(newWorkbook.getId());

        // 오답 그룹 있으면 거기에, 없으면 새로 생성
        IncorrectsGroupDTO groupdto = groupService.getOrCreateReviewGroup(studentId);
        // 그룹에 문제집 등록
        Group group = mapper.map(groupdto, Group.class);
        List<String> wIds = group.getWorkbookIds();
        wIds.add(newWorkbook.getId());
        group.setWorkbookIds(wIds);
        groupRepository.save(group);
        // 문제집 id 리턴
        return mapper.map(newWorkbook, WorkbookIdResponseDto.class);
    }

    @Override
    public IncorrectsGroupDTO getMyReviewGroup(String studentId) {
        return groupService.getOrCreateReviewGroup(studentId);
    }

    @Override
    public List<WorkbookDto> getReviewWorkbooks(String studentId) {
        IncorrectsGroupDTO groupDto = groupService.getOrCreateReviewGroup(studentId);
        List<Workbook> workbooks = workbookRepository.findByIdIn(groupDto.getWorkbookIds());
        List<WorkbookDto> workbookDtos = workbooks.stream()
                .map(workbook -> mapper.map(workbook, WorkbookDto.class)) // WorkbookDto 생성자 활용
                .collect(Collectors.toList());

        return workbookDtos;
    }

    @Transactional
    @Override
    public void deleteRecordByQuestionId(String questionId) {
        incorrectAnswerTagRepository.deleteAllByQuestionId(questionId);
    }

}
