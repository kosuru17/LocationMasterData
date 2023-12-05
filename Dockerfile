FROM openjdk:17
EXPOSE 8085
ADD target/consumer-query-image.jar consumer-query-image.jar
ENTRYPOINT ["java","-jar","/consumer-query-image.jar"]