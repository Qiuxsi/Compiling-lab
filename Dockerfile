FROM ubuntu:16.04
RUN apt-get update && apt-get install g++ -y
WORKDIR /app/
COPY cifafenxi.c ./
RUN g++ cifafenxi.c -o cifafenxi