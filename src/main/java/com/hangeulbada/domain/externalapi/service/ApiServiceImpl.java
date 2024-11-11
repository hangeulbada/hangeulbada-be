package com.hangeulbada.domain.externalapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hangeulbada.domain.workbookset.dto.ClaudeRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiServiceImpl {

    @Value("${python.api.url}") String pythonBaseURL;

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
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonBody = objectMapper.writeValueAsString(requestDto);

            // HttpEntity에 JSON 본문 설정
            HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);

            // HTTP POST 요청 보내기
            ResponseEntity<String> responseEntity = new RestTemplate().postForEntity(apiUrl, requestEntity, String.class);
            responseBody = responseEntity.getBody();
        } catch (Exception e) {
            log.error("JSON 변환 또는 POST 요청 실패", e);
        }
        return responseBody;
    }

}
