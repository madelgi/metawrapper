#########################################
# Phase 1: Build
#########################################
FROM maven:3.5.3-jdk-8 AS build
ARG pkg="metawrapper"

# Create non-root user for our container
RUN useradd -ms /bin/bash metawrapper
RUN mkdir /home/metawrapper/service
WORKDIR /home/metawrapper/service

RUN apt-get update && apt-get install -y --no-install-recommends openjfx && apt-get install -y netcat && rm -rf /var/lib/apt/lists/*

COPY . .
RUN chmod +x bin
RUN ./bin/install_dependencies.sh
RUN mvn -f pom.xml -Dmaven.test.skip=true clean package

CMD ./bin/start_metawrapper.sh
