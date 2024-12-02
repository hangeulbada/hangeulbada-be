package com.hangeulbada.domain.tts.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.hangeulbada.domain.workbookset.exception.S3Exception;
import com.hangeulbada.domain.workbookset.exception.TtsException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class TTSService {
    // S3 설정
    @Value("${cloud.aws.s3.bucket.name}")
    private String bucketName;
    @Value("${cloud.aws.s3.bucket.region}")
    private String region;
    @Value("${cloud.aws.s3.access.key}")
    private String awsAccessKeyId;
    @Value("${cloud.aws.s3.access.secret}")
    private String awsSecretAccessKey;

    // TTS 설정
    @Value("${clova.tts.client.id}")
    private String clientId;
    @Value("${clova.tts.client.secret}")
    private String clientSecret;

    private AmazonS3 s3Client;

    @PostConstruct
    public void init() {
        try {
            BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsAccessKeyId, awsSecretAccessKey);
            this.s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(region)
                    .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                    .build();
        } catch (Exception e) {
            throw new S3Exception("S3 클라이언트 초기화에 실패했습니다.");
        }
    }

    public String tts(String questionText) {
        try {
            String text = URLEncoder.encode(questionText, "UTF-8");
            String apiURL = "https://naveropenapi.apigw.ntruss.com/tts-premium/v1/tts";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
            con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
            // post request
            String postParams = "speaker=nara&volume=2&speed=0&pitch=0&format=mp3&text=" + text;
            con.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.writeBytes(postParams);
                wr.flush();
            }

            int responseCode = con.getResponseCode();
            if (responseCode == 200) { // 정상 호출
                try (InputStream is = con.getInputStream()) {
                    String fileName = Long.toString(new Date().getTime()) + ".mp3";
                    String s3FilePath = uploadFileToS3(is, fileName);
                    return s3FilePath;
                }
            } else {  // 오류 발생
                try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getErrorStream()))) {
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = br.readLine()) != null) {
                        response.append(inputLine);
                    }
                    throw new TtsException("TTS API 호출 오류: " + response.toString());
                }
            }
        } catch (Exception e) {
            throw new TtsException("TTS 변환 중 오류 발생");
        }
    }
    private String uploadFileToS3(InputStream inputStream, String fileName) {
            try {
                byte[] fileData = inputStream.readAllBytes(); // 전체 데이터를 읽어 byte 배열로 변환
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentType("audio/mpeg");
                metadata.setContentLength(fileData.length); // 파일 크기를 명시적으로 설정

                // ByteArrayInputStream을 사용해 다시 InputStream으로 변환
                try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fileData)) {
                    s3Client.putObject(new PutObjectRequest(bucketName, fileName, byteArrayInputStream, metadata));
                }
                String fileUrl = s3Client.getUrl(bucketName, fileName).toString();
                return fileUrl;
            } catch (Exception e) {
                throw new S3Exception("파일 업로드 중 오류 발생");
            }
        }
    public static String extractFileName(String url) {
        if (url == null || url.isEmpty()) {
            return null;
        }
        int lastSlashIndex = url.lastIndexOf('/');
        if (lastSlashIndex == -1) {
            return null;
        }
        return url.substring(lastSlashIndex + 1);
    }
    public void deleteFileFromS3(String filePath){
        try{
            String fileName = extractFileName(filePath);
            s3Client.deleteObject(bucketName,fileName);
        }catch(Exception e){
            throw new S3Exception("음성 파일을 삭제하던 중 에러가 발생했습니다.");
        }
    }
}
