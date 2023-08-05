# FROM camunda/camunda-bpm-platform:run-7.19.0
FROM camunda/camunda-bpm-platform:7.20.0-SNAPSHOT
# COPY camunda/userlib /camunda/configuration/userlib
# COPY camunda/bpmn /camunda/configuration/resources
# COPY camunda/webapp  /camunda/internal/example
COPY camunda/webapp /camunda/webapps/
COPY camunda/userlib /camunda/lib/
# RUN mkdir -p /build
COPY camunda /build