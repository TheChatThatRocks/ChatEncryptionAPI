# Build
FROM openjdk:11-jdk as build
WORKDIR /workspace/app
COPY gradle gradle
COPY src src
COPY build.gradle .
COPY gradlew .
COPY gradlew.bat .
# COPY settings.gradle .

# Build app
RUN chmod +x /workspace/app/gradlew
RUN /workspace/app/gradlew clean build -x test

# Extranct files
RUN mkdir -p build/dependency && (cd /workspace/app/build/dependency; jar -xf /workspace/app/build/libs/*.jar)

# Execution
FROM openjdk:11-jdk
EXPOSE 7799

# VOLUME /tmp
ENV DEPENDENCY=/workspace/app/build/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","com.eina.encryption.chat.CryptoApplication"]
