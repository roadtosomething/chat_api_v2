FROM openjdk:21-ea-1-jdk-oracle
WORKDIR /jtapp
COPY . /jtapp
RUN ./mvnw package -DskipTests  # Для Maven
# RUN ./gradlew build -x test   # Для Gradle
RUN ls -la /jtapp/target          # Вставьте для проверки наличия jar файла
CMD ["java", "-jar", "target/chat_api_v2-0.0.1-SNAPSHOT.jar"]