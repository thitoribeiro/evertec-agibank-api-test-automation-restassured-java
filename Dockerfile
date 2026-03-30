FROM maven:3.9-eclipse-temurin-17-alpine
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -q
COPY src ./src
ENV BASE_URL=https://dog.ceo/api
ENV TAGS=""
CMD ["sh", "-c", "mvn test -Dallure.results.directory=target/allure-results ${TAGS:+-Dgroups=$TAGS}"]
