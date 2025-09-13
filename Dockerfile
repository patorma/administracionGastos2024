FROM openjdk:21-jdk-slim
ARG JAR_FILE=target/administracion-gastos-0.0.1.jar
COPY ${JAR_FILE} administracion-gastos.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "administracion-gastos.jar"]