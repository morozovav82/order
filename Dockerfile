FROM frolvlad/alpine-java:jdk8.202.08-slim
COPY target/order-1.1.jar /
CMD ["java", "-jar", "order-1.1.jar"]
