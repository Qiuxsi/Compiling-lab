FROM gcc:11
WORKDIR /app/
COPY main.c ./
RUN gcc main.c -o main -lm
RUN chmod +x main