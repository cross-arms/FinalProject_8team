FROM openjdk:17-jdk-slim
EXPOSE 8080
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENV SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE}
ENV JWT_SECRET=+PViUi4kZ5G5m7x5oZQ/jfSDt8IQ1PAWyAt3L8UoHjZ5VlBpiu84vCAS5NuLGJ/F
ENV SPRING_MAIL_USERNAME=abc@abc.com
ENV SPRING_MAIL_PASSWORD=welcome1234
ENTRYPOINT ["java","-jar", "/app.jar"]