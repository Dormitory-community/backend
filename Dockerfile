# 빌드 스테이지
FROM gradle:8.5-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle build --no-daemon -x test

# 실행 스테이지
FROM eclipse-temurin:17-jre
WORKDIR /app

RUN apk add --no-cache tzdata
ENV TZ=Asia/Seoul

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=build --chown=spring:spring /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
EXPOSE 8080
