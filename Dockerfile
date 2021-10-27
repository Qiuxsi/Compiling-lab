FROM openjdk:16
WORKDIR /app/
COPY src ./src/
RUN javac -encoding UTF-8 -cp src -d out src/test/Test.java