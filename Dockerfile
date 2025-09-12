# 빌드 스테이지 (멀티 아키텍처 지원)
FROM --platform=$BUILDPLATFORM gradle:8.5-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle build --no-daemon -x test

# 실행 스테이지 (멀티 아키텍처 지원)
FROM --platform=$BUILDPLATFORM eclipse-temurin:17-jre-alpine
WORKDIR /app

# 타임존 설정
RUN apk add --no-cache tzdata
ENV TZ=Asia/Seoul

# 애플리케이션 실행을 위한 사용자 추가
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# 빌드 스테이지에서 생성된 JAR 파일 복사
COPY --from=build --chown=spring:spring /app/build/libs/*.jar app.jar

# 컨테이너가 시작될 때 실행될 명령어
ENTRYPOINT ["java", "-jar", "app.jar"]

# 컨테이너 포트 노출
EXPOSE 8080