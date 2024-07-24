# 기본 이미지 설정
FROM openjdk:21-jdk

# 애플리케이션 파일 복사
COPY build/libs/demo-0.0.1-SNAPSHOT.jar /demo.jar

# 포트 설정
EXPOSE 5943

# 환경 설정 파일 복사
COPY ./application.yml ./application-oauth.yml /config/

# 애플리케이션 실행 명령어 설정
ENTRYPOINT ["java", "-jar", "/demo.jar", "--spring.config.location=/config/application.yml,/config/application-oauth.yml"]
