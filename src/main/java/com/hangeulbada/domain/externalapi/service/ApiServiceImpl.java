package com.hangeulbada.domain.externalapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hangeulbada.domain.assignment.dto.AssignmentScoreRequestDto;
import com.hangeulbada.domain.assignment.dto.AssignmentScoreResponseDTO;
import com.hangeulbada.domain.workbookset.dto.ClaudeRequestDto;
import com.hangeulbada.domain.workbookset.dto.QuestionDiffRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiServiceImpl implements ApiService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${python.api.url}") String pythonBaseURL;
    @Value("${cloud.aws.s3.bucket.url}") String s3Url;

    // claude
    public String postToClaude(ClaudeRequestDto requestDto) {
        // 요청을 보낼 URL
        String apiUrl = pythonBaseURL + "/claude";

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String responseBody = "";
        try {
            // JSON 형식으로 변환
            String jsonBody = objectMapper.writeValueAsString(requestDto);

            // HttpEntity에 JSON 본문 설정
            HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);

            // HTTP POST 요청 보내기
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(apiUrl, requestEntity, String.class);
            responseBody = responseEntity.getBody();
        } catch (Exception e) {
            log.error("JSON 변환 또는 POST 요청 실패", e);
        }
        return responseBody;
    }

    public Integer calculateQuestionDiff(String text) {
        // 요청을 보낼 URL
        String apiUrl = pythonBaseURL + "/difficulty";
        Integer diff = 0;

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        String jsonBody = "";
        try {
            jsonBody = objectMapper.writeValueAsString(new QuestionDiffRequestDto(text));
        } catch (Exception e) {
            e.printStackTrace();
            return diff;
        }

        HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                diff = Integer.parseInt(response.getBody());
            } else {
                System.err.println("Error: " + response.getStatusCode() + " - " + response.getBody());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return diff;
    }

    public AssignmentScoreResponseDTO postAssignmentScore(AssignmentScoreRequestDto requestDto) {
        // 요청을 보낼 URL
        String apiUrl = pythonBaseURL + "/score";

        // OCR 요청 이미지 파일 루트
        requestDto.setAnswer(s3Url + requestDto.getAnswer());

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        String jsonBody = "";
        try {
            jsonBody = objectMapper.writeValueAsString(requestDto);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);
            if (response != null && response.getStatusCode() == HttpStatus.OK) {
                AssignmentScoreResponseDTO responseDTO  = objectMapper.readValue(response.getBody(), AssignmentScoreResponseDTO.class);
                if(responseDTO ==null) System.err.println("objectMapper 문제");
                log.info(responseDTO.toString());
                return responseDTO;
            } else {
                System.err.println("API 호출 중 오류 발생: " + response.getStatusCode() + " - " + response.getBody());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

}
