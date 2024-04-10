package com.hangeulbada.domain.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hangeulbada.domain.auth.dto.AuthTokens;
import com.hangeulbada.domain.auth.dto.LoginResponse;
import com.hangeulbada.domain.auth.dto.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import com.hangeulbada.domain.auth.dto.User;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleService {

    private final AuthTokensGenerator authTokensGenerator;
    private final UserRepository userRepository;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String googleClientSecret;
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String googleRedirectUri;

    public LoginResponse googleLogin(String code){
        // code로 access token 요청
        String accessToken = getGoogleAccessToekn(code, googleRedirectUri);
        log.info("accessToken: "+accessToken);
        // access token으로 사용자 정보 요청
        HashMap<String, Object> userInfo = getGoogleUserInfo(accessToken);
        log.info("userInfo: "+userInfo);

        // google id로 회원가입, 로그인 처리
        LoginResponse googleUserResponse = googleUserLogin(userInfo);
        log.info("googleUserResponse: "+googleUserResponse);
        return googleUserResponse;
    }

    // 인가 code로 accesstoken 요청
    private String getGoogleAccessToekn(String code, String googleRedirectUri){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", googleClientId);
        body.add("client_secret", googleClientSecret);
        body.add("redirect_uri", googleRedirectUri);
        body.add("code", code);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://oauth2.googleapis.com/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(responseBody);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonNode.get("access_token").asText(); //토큰 전송
    }

    // accesstoken으로 사용자 정보 요청
    private HashMap<String, Object> getGoogleUserInfo(String accessToken) {
        log.info("getGoogleUserInfo");
        HashMap<String, Object> userInfo= new HashMap<String,Object>();

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://www.googleapis.com/oauth2/v1/userinfo",
                HttpMethod.GET,
                kakaoUserInfoRequest,
                String.class
        );
        log.info("response: "+response);

        // responseBody에 있는 정보를 꺼냄
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;

        try {
            jsonNode = objectMapper.readTree(responseBody);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        log.info("jsonNode: "+jsonNode);
        String id = String.valueOf(jsonNode.get("id"));
        String email = jsonNode.get("email").asText();
        String name = jsonNode.get("name").asText();

        userInfo.put("id",id);
        userInfo.put("email",email);
        userInfo.put("name",name);

        return userInfo;
    }

    // 사용자 정보로 회원가입, 로그인 처리
    private LoginResponse googleUserLogin(HashMap<String, Object> userInfo) {
        log.info("googleUserLogin");
        String uid = (String) userInfo.get("id");
        String email = (String) userInfo.get("email");
        String name = (String) userInfo.get("name");

        User googleUser = userRepository.findByUid(uid)
                .orElseGet(() -> {
                    User newUser = User.builder()
                            .uid(uid)
                            .email(email)
                            .name(name)
                            .loginType("google")
                            .build();
                    return userRepository.save(newUser);
                });

        AuthTokens token=authTokensGenerator.generate((String) userInfo.get("id"));

        log.info("token: "+token.toString());
        return new LoginResponse(googleUser.getId(),googleUser.getName(),googleUser.getEmail(), token);


    }
}
