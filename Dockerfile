FROM java:8
RUN mkdir /lecture
WORKDIR /lecture
COPY build/distributions/document-service.tar document-service.tar
RUN tar -xf document-service.tar
EXPOSE 8080
ENTRYPOINT document-service/bin/document-service
