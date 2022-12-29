FROM mongo:latest
RUN apt-get update && apt-get install tree
EXPOSE 27017



