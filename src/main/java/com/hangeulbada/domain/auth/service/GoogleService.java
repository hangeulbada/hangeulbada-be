package com.hangeulbada.domain.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hangeulbada.domain.auth.component.AuthTokensGenerator;
import com.hangeulbada.domain.auth.dto.AuthTokens;
import com.hangeulbada.domain.auth.dto.LoginResponse;
import com.hangeulbada.domain.auth.dto.SignupResponse;
import com.hangeulbada.domain.auth.entity.User;
import com.hangeulbada.domain.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    public LoginResponse googleSignup(SignupResponse signupResponse) {

        log.info("googleSignup");
        String uid = (String) signupResponse.getUid();
        String email = (String) signupResponse.getEmail();
        String name = (String) signupResponse.getName();
        String role = (String) signupResponse.getRole();

        // User 데이터베이스에 저장
        User newUser = User.builder()
                .uid(uid)
                .email(email)
                .name(name)
                .role(role)
                .build();

        AuthTokens token = authTokensGenerator.generate(userRepository.save(newUser).getId());
        return LoginResponse.builder()
                .id(newUser.getId())
                .uid(newUser.getUid())
                .name(newUser.getName())
                .email(newUser.getEmail())
                .role(newUser.getRole())
                .token(token)
                .build();
    }

    public LoginResponse googleOauth2(String code) {
        // code로 access token 요청
        String accessToken = getGoogleAccessToken(code, googleRedirectUri);
        log.info("accessToken: " + accessToken);
        // access token으로 사용자 정보 요청
        HashMap<String, Object> userInfo = getGoogleUserInfo(accessToken);
        // google id로 회원가입, 로그인 처리
        return googleUserLogin(userInfo);
    }

    LoginResponse googleUserLogin(HashMap<String, Object> userInfo) {
        log.info("googleUserLogin");
        String uid = (String) userInfo.get("uid");

        // 이미 가입된 회원인지 확인
        log.info("이미 가입된 회원인지 확인" + uid);
        Optional<User> user = userRepository.findByUid(uid);
        if (user.isPresent()) {
            User googleUser = user.get();
            AuthTokens token = authTokensGenerator.generate(uid);
            return LoginResponse.builder()
                    .id(googleUser.getId())
                    .uid(googleUser.getUid())
                    .name(googleUser.getName())
                    .email(googleUser.getEmail())
                    .role(googleUser.getId())
                    .token(token)
                    .build();
        }
        // 가입되지 않은 회원
        else {
            log.info("가입되지 않은 회원, 회원가입 필요");
            return LoginResponse.builder()
                    .uid(userInfo.get("uid").toString())
                    .name(userInfo.get("name").toString())
                    .email(userInfo.get("email").toString())
                    .build();
        }
    }

    // 인가 code로 accesstoken 요청
    private String getGoogleAccessToken(String code, String googleRedirectUri) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        log.error("유효하지 않은 code");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", googleClientId);
        body.add("client_secret", googleClientSecret);
        body.add("redirect_uri", googleRedirectUri);
        body.add("code", code);
        log.error("유효하지 않은 code");

        // HTTP 요청 보내기
        try {
            HttpEntity<MultiValueMap<String, String>> googleTokenRequest = new HttpEntity<>(body, headers);
            RestTemplate rt = new RestTemplate();
            ResponseEntity<String> response = rt.exchange(
                    "https://oauth2.googleapis.com/token",
                    HttpMethod.POST,
                    googleTokenRequest,
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
        } catch (Exception e) {
            log.error("유효하지 않은 code");
            throw new NoSuchElementException();
        }
    }

    // accesstoken으로 사용자 정보 요청
    private HashMap<String, Object> getGoogleUserInfo(String accessToken) {
        log.info("getGoogleUserInfo");
        HashMap<String, Object> userInfo = new HashMap<String, Object>();

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> googleUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://www.googleapis.com/oauth2/v1/userinfo",
                HttpMethod.GET,
                googleUserInfoRequest,
                String.class
        );
        log.info("response: " + response);

        // responseBody에 있는 정보를 꺼냄
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;

        try {
            jsonNode = objectMapper.readTree(responseBody);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        log.info("jsonNode: " + jsonNode);
        String uid = jsonNode.get("id").asText();
        String email = jsonNode.get("email").asText();
        String name = jsonNode.get("name").asText();

        userInfo.put("uid", uid);
        userInfo.put("email", email);
        userInfo.put("name", name);

        return userInfo;
    }
}
