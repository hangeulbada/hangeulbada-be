package com.hangeulbada.domain.tts.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;


@Service
public class TTSService {
    @Value("${aws.s3.bucket.name}") String bucketName;
    @Value("${aws.access.key.id}") String awsAccessKeyId;
    @Value("${aws.secret.access.key}") String awsSecretAccessKey;
    @Value("${aws.s3.region}") String region;
    private final AmazonS3 s3Client;
    public TTSService() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsAccessKeyId, awsSecretAccessKey);
        this.s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
    }
    public String tts(String questionText) {
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
            if (responseCode == 200) { // 정상 호출
                InputStream is = con.getInputStream();
                String fileName = Long.toString(new Date().getTime()) + ".mp3";
                String s3FilePath = uploadFileToS3(is, fileName);
                is.close();
                return s3FilePath;
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
        return null;
    }
    private String uploadFileToS3(InputStream inputStream, String fileName) {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("audio/mpeg"); // MP3 파일인 경우에는 적절한 Content-Type으로 설정해야 합니다.
            s3Client.putObject(new PutObjectRequest(bucketName, fileName, inputStream, metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead)); // 업로드된 파일에 대해 public read 권한을 부여합니다.
            return s3Client.getUrl(bucketName, fileName).toString(); // 업로드된 파일의 URL을 반환합니다.
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
}
}
