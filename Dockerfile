FROM 786796192785.dkr.ecr.eu-west-1.amazonaws.com/10x/10x-infer:latest AS infer
FROM 786796192785.dkr.ecr.eu-west-1.amazonaws.com/10x/10x-openjdk-11-ssm:latest AS build

COPY --from=infer /infer /infer
ENV PATH /infer/bin:${PATH}

ADD . /src
WORKDIR /src
ARG SONARQUBE_SCANNER_PARAMS='{}'
ARG COMMIT_ID
ENV COMMIT_ID=$COMMIT_ID
LABEL archive.test.results=$COMMIT_ID
LABEL archive.infer.results=$COMMIT_ID
RUN apt-get update && apt-get install -y git

RUN ./gradlew --no-daemon \
    clean \
    sonarqube \
    godel-kafka-course-service:bootJar; \
    echo $? > $?.exit;

RUN ./gradlew --no-daemon --stacktrace inferMutate godel-kafka-course-service:inferAnalyze godel-kafka-course:inferReport

RUN echo "Copying infer results"
RUN mkdir -p ./build/infer-results/
RUN mv ./infer-report/report.xml ./build/infer-results

RUN mkdir -p build/test-results \
  && cp -r ./**/build/test-results/test/*.xml ./build/test-results

RUN if [ ! -f "0.exit" ]; then echo "Gradle build FAILED"; exit 1;  fi

FROM ${AWS_ACCOUNT_NO:-786796192785}.dkr.ecr.eu-west-1.amazonaws.com/10x/10x-openjre-11-ssm:latest
EXPOSE 8080
HEALTHCHECK --retries=12 --interval=10s CMD curl -s localhost:8080/health || exit 1
COPY --from=build /src/godel-kafka-course/build/libs/godel-kafka-course.jar /usr/local/bin/service.jar
COPY --from=build /src/run.sh /usr/local/bin/run.sh

RUN chmod +x /usr/local/bin/service.jar
RUN chmod +x /usr/local/bin/run.sh
RUN chown -R tenx:tenx /usr/local/bin/service.jar /usr/local/bin/run.sh

CMD ["chamber", "exec", "godel-kafka-course", "--", "/usr/local/bin/run.sh"]