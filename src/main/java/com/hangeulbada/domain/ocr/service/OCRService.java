package com.hangeulbada.domain.ocr.service;

import com.hangeulbada.domain.assignment.dto.ScoreDTO;
import com.hangeulbada.domain.ocr.dto.OCRRequest;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;


@Service
@Slf4j
@RequiredArgsConstructor
public class OCRService {
    @Value("${clova.ocr.secretkey}") String secretKey;
    @Value("${clova.ocr.api.url}") String apiURL;


    public List<ScoreDTO> startOcr(OCRRequest ocrRequest){
        String ocrText = start(ocrRequest.getImageName());
        log.info("ocrText = " + ocrText);
        return null;
    }

    public String start(String fileName) {

        try {
            String S3Url = "https://bada-static-bucket.s3.ap-northeast-2.amazonaws.com/" +fileName+".jpeg";

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
            image.put("format", "jpeg");
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
            log.info("response = " + response.toString());
            String result = response.getBody();
            log.info("result = " + result);

            return getInferText(result);
        } catch (Exception e) {
            System.out.println("e1" + e);
        }
        return null;
    }

    public String getInferText(String str) {
        List<String> inferTextList = new ArrayList<>();

        JSONObject json = new JSONObject(str);
        log.info("json = " + json.toString());
        JSONArray images = json.getJSONArray("images");
        JSONArray fields = images.getJSONObject(0).getJSONArray("fields");

        for (int i = 0; i < fields.length(); i++) {
            JSONObject field = fields.getJSONObject(i);
            String inferText = field.getString("inferText");
            inferTextList.add(inferText);
        }

        System.out.println(inferTextList);

        return getPrettyInfer(inferTextList).toString();
    }

    public List<String> getPrettyInfer(List<String> inferTextList) {
        String concat = inferTextList.toString().replace("[", "").replace("]", "").replace(",", "");

        char[] chars = concat.toCharArray();
        List<String> resultList = new ArrayList<>();
        StringBuilder currentString = new StringBuilder();
        int startNum=0;
        for (int i=0;i<chars.length;i++) {
            if (Character.isDigit(chars[i])) {
                if (!currentString.isEmpty()) {
                    resultList.add(currentString.toString());
                    currentString = new StringBuilder();
                }

                while (Character.isDigit(chars[i])){
                    currentString.append(chars[i]);
                    if (startNum+1==Integer.parseInt(currentString.toString())){
                        break;
                    }
                    if (Character.isDigit(chars[i+1])) {
                        i++;
                    }
                    else{
                        break;
                    }
                }
                if (startNum==0){
                    startNum = Integer.parseInt(currentString.toString());
                }

            } else{
                if (!currentString.isEmpty() && Character.isDigit(currentString.charAt(currentString.length() - 1))) {
                    currentString.append(".");
                }
                currentString.append(chars[i]);
            }
        }

        if (!currentString.isEmpty()) {
            resultList.add(currentString.toString());
        }

        // 결과 출력
        for (String result : resultList) {
            System.out.println(result);
        }

        return resultList;
    }


    public void writeMultiPart(OutputStream out, String jsonMessage, File file, String boundary) throws
            IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("--").append(boundary).append("\r\n");
        sb.append("Content-Disposition:form-data; name=\"message\"\r\n\r\n");
        sb.append(jsonMessage);
        sb.append("\r\n");

        out.write(sb.toString().getBytes("UTF-8"));
        out.flush();

        if (file != null && file.isFile()) {
            out.write(("--" + boundary + "\r\n").getBytes("UTF-8"));
            StringBuilder fileString = new StringBuilder();
            fileString
                    .append("Content-Disposition:form-data; name=\"file\"; filename=");
            fileString.append("\"" + file.getName() + "\"\r\n");
            fileString.append("Content-Type: application/octet-stream\r\n\r\n");
            out.write(fileString.toString().getBytes("UTF-8"));
            out.flush();

            try (FileInputStream fis = new FileInputStream(file)) {
                byte[] buffer = new byte[8192];
                int count;
                while ((count = fis.read(buffer)) != -1) {
                    out.write(buffer, 0, count);
                }
                out.write("\r\n".getBytes());
            }

            out.write(("--" + boundary + "--\r\n").getBytes("UTF-8"));
        }
        out.flush();
    }

    public void submit(int studentId, String ocrImage) {
        System.out.println("studentId = " + studentId);
        System.out.println("ocrImage = " + ocrImage);
    }

}
