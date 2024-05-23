package com.hangeulbada.domain.ocr.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class OCRService {
    @Value("${clova.ocr.secretkey}") String secretKey;
    @Value("${clova.ocr.api.url}") String apiURL;
    @Value("${cloud.aws.s3.bucket.url}") String s3Url;

    public List<String> startOCR(String fileName) {
        try {
            String format = fileName.split("\\.")[1];
            String S3Url = s3Url + fileName;

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");
            headers.add("X-OCR-SECRET", secretKey);

            Map<String, Object> body = new HashMap<>();
            body.put("lang", "ko");
            body.put("version", "V2");
            body.put("requestId", UUID.randomUUID().toString());
            body.put("timestamp", String.valueOf(System.currentTimeMillis()));

            List<Map<String, String>> images = new ArrayList<>();
            Map<String, String> image = new HashMap<>();
            image.put("format", format);
            image.put("name", fileName);
            image.put("url", S3Url);
            images.add(image);

            body.put("images", images); // Directly put the List as an object, not a string

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

            RestTemplate rt = new RestTemplate();
            ResponseEntity<String> response = rt.exchange(
                    apiURL,
                    HttpMethod.POST,
                    entity,
                    String.class
            );
            String result = response.getBody();
            List<String> rr = getInferText(result);
            System.out.println(rr);

            return rr;
        } catch (Exception e) {
            log.error("Error during OCR process: ", e);
        }
        return Collections.emptyList();
    }

    // ocr data에서 infertext만 파싱
    public List<String> getInferText(String str) {
        List<String> inferTextList = new ArrayList<>();

        try {
            JSONObject json = new JSONObject(str);
            JSONArray images = json.getJSONArray("images");
            JSONArray fields = images.getJSONObject(0).getJSONArray("fields");

            for (int i = 0; i < fields.length(); i++) {
                JSONObject field = fields.getJSONObject(i);
                String inferText = field.getString("inferText");
                inferTextList.add(inferText);
            }

            log.debug("Extracted infer text: {}", inferTextList);

            return getPrettyInfer(inferTextList);
        } catch (Exception e) {
            log.error("Error parsing infer text: ", e);
        }

        return Collections.emptyList();
    }

    // 추출된 문자열 보정
    public List<String> getPrettyInfer(List<String> inferTextList) {
        String concat = inferTextList.toString().replace("[", "").replace("]", "").replace(",", "").replace(".", "");

        char[] chars = concat.toCharArray();
        List<String> resultList = new ArrayList<>();
        StringBuilder currentString = new StringBuilder();
        int startNum = 0;

        try {
            for (int i = 0; i < chars.length; i++) {
                if (Character.isDigit(chars[i])) {
                    if (currentString.length() > 0) {
                        resultList.add(currentString.toString());
                        currentString = new StringBuilder();
                    }

                    while (i < chars.length && Character.isDigit(chars[i])) {
                        currentString.append(chars[i]);
                        if (startNum + 1 == Integer.parseInt(currentString.toString())) {
                            break;
                        }
                        if (i + 1 < chars.length && Character.isDigit(chars[i + 1])) {
                            i++;
                        } else {
                            break;
                        }
                    }
                    if (startNum == 0) {
                        startNum = Integer.parseInt(currentString.toString());
                    }

                } else {
                    if (currentString.length() > 0 && Character.isDigit(currentString.charAt(currentString.length() - 1))) {
                        currentString.append(".");
                    }
                    currentString.append(chars[i]);
                }
            }

            if (currentString.length() > 0) {
                resultList.add(currentString.toString());
            }

            log.debug("Formatted result: {}", resultList);

        } catch (Exception e) {
            log.error("Error formatting infer text: ", e);
        }

        return resultList;
    }
}
