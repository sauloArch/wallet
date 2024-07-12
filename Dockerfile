FROM openjdk:17-jdk-alpine
VOLUME /tmp
COPY target/pic-wallet.jar pic-wallet.jar
ENTRYPOINT ["java","-jar","/pic-wallet.jar"]