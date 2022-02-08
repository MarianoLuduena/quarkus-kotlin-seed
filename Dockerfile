FROM registry.access.redhat.com/ubi8/openjdk-11:1.11 as build-env
COPY --chown=185 . /home/jboss/app
WORKDIR /home/jboss/app
RUN ./gradlew clean build --info --stacktrace --no-daemon

FROM registry.access.redhat.com/ubi8/openjdk-11-runtime:1.11
ENV JAVA_OPTIONS="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"
COPY --chown=185 --from=build-env /home/jboss/app/build/quarkus-app/lib/ /deployments/lib/
COPY --chown=185 --from=build-env /home/jboss/app/build/quarkus-app/*.jar /deployments/
COPY --chown=185 --from=build-env /home/jboss/app/build/quarkus-app/app/ /deployments/app/
COPY --chown=185 --from=build-env /home/jboss/app/build/quarkus-app/quarkus/ /deployments/quarkus/
EXPOSE 8080
USER 185
ENTRYPOINT [ "java", "-jar", "/deployments/quarkus-run.jar" ]
