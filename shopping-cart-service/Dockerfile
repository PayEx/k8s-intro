FROM openjdk:10-jre

ENTRYPOINT ["/usr/bin/java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-jar", "/usr/share/shopping-cart-service/app.jar"]

# Add service
ARG JAR_FILE
ADD target/${JAR_FILE} /usr/share/shopping-cart-service/app.jar

EXPOSE 3400
