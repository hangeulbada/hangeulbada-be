package com.hangeulbada.domain.ocr.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

@Service
@RequiredArgsConstructor
public class OCRService {
    @Value("${clova.ocr.secretkey}") String secretKey;
    @Value("${clova.ocr.api.url}") String apiURL;

    public static String imageFile = "D:\\hangeul_bada\\src\\main\\resources\\static\\ocr_1.png";

    public String start() {
        try {
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setReadTimeout(30000);
            con.setRequestMethod("POST");
            String boundary = "----" + UUID.randomUUID().toString().replaceAll("-", "");
            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            con.setRequestProperty("X-OCR-SECRET", secretKey);

            JSONObject json = new JSONObject();
            json.put("version", "V2");
            json.put("requestId", UUID.randomUUID().toString());
            json.put("timestamp", System.currentTimeMillis());
            JSONObject image = new JSONObject();
            image.put("format", "jpg");
            image.put("name", "demo");
            JSONArray images = new JSONArray();
            System.out.println("image = " + image.toString());
            images.put(image);
            json.put("images", images);

            String postParams = json.toString();

            con.connect();
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            long start = System.currentTimeMillis();
            File file = new File(imageFile);
            writeMultiPart(wr, postParams, file, boundary);
            wr.close();

            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            // response to json
            String parsedResponse = getInferText(response.toString());

            System.out.println(parsedResponse);
            return parsedResponse;
        } catch (Exception e) {
            System.out.println(e);
        }
//        getPrettyInfer(List.of("6", "밝은 해임치", "7 방화", "게해에 을세워요", "8", "좋은", "책을 많이", "있짜", "9", "6학년이", "됐던 좋아", "10"," 받아쓰기는", "종요해"));
        return null;
    }

    public String getInferText(String str) {
        List<String> inferTextList = new ArrayList<>();

        JSONObject json = new JSONObject(str);
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

    public String tts() {
        String clientId = "nu7h0ox9o9";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "mMuPX3WMWVRE8YqyO4x627ypW2Nv2ekgsVwMq0uf";//애플리케이션 클라이언트 시크릿값";
        try {
            String text = URLEncoder.encode("한글바다.", "UTF-8"); // 13자
            String apiURL = "https://naveropenapi.apigw.ntruss.com/tts-premium/v1/tts";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
            con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
            // post request
            String postParams = "speaker=nara&volume=0&speed=0&pitch=0&format=mp3&text=" + text;
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                InputStream is = con.getInputStream();
                int read = 0;
                byte[] bytes = new byte[1024];
                // 랜덤한 이름으로 mp3 파일 생성
                String tempname = Long.valueOf(new Date().getTime()).toString();
                File f = new File(tempname + ".mp3");
                f.createNewFile();
                OutputStream outputStream = new FileOutputStream(f);
                while ((read =is.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
                is.close();
            } else {  // 오류 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
                System.out.println(response.toString());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return "tts";
    }
}
