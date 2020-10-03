FROM metamap2018:latest

RUN apt-get update && apt-get install -y netcat

COPY ./bin/start_metamap.sh .
RUN chmod +x start_metamap.sh

ENTRYPOINT ["./start_metamap.sh"]
