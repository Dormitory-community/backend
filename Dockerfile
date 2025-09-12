# 실행 스테이지
FROM eclipse-temurin:17-jre

WORKDIR /app

# 타임존 설치 (Debian/Ubuntu용)
RUN apt-get update && apt-get install -y tzdata && rm -rf /var/lib/apt/lists/*

ENV TZ=Asia/Seoul

# 사용자 추가
RUN groupadd -r spring && useradd -r -g spring spring
USER spring:spring

# JAR 파일 복사
COPY --from=build --chown=spring:spring /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
EXPOSE 8080
