FROM gcc:10
WORKDIR /app/
COPY cifafenxi.c ./
RUN gcc cifafenxi.c -o cifafenxi
RUN chmod +x cifafenxi