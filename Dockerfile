FROM frolvlad/alpine-java:jdk8.202.08-slim
COPY target/order-2.0.jar /
CMD ["java", "-jar", "order-2.0.jar"]
