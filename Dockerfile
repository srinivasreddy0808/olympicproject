FROM openjdk:17

WORKDIR /app

# Copy compiled class files and resources
COPY out /app/out
COPY resources /app/resources

# Entry point: run Main class
CMD ["java", "-cp", "out", "Main"]
