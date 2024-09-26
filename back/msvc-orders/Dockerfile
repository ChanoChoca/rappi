ARG MSVC_NAME=msvc-orders

FROM openjdk:24-slim as builder

ARG MSVC_NAME

# Set the working directory inside the container
WORKDIR /app/$MSVC_NAME

# Copy the pom.xml file from the host to the /app directory in the container
COPY ./pom.xml /app

# Copy the .mvn directory from the host to the current working directory in the container
COPY ./$MSVC_NAME/.mvn ./.mvn

# Copy the mvnw file from the host to the current working directory in the container
COPY ./$MSVC_NAME/mvnw .

#Copy the pom.xml file from the host to the current working directory in the container
COPY ./$MSVC_NAME/pom.xml .

# Run Maven to clean and package the project without running tests and deleting the target directory
RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

#Copy the application source code from the host to the src directory in the container
COPY ./$MSVC_NAME/src ./src

# Run Maven again to clean and package the project, this time without skipping tests
RUN ./mvnw clean package -DskipTests

# Use a lighter OpenJDK base image for the final container
FROM openjdk:24-slim

ARG MSVC_NAME

# Set the working directory inside the final container
WORKDIR /app
RUN mkdir ./logs

ARG TARGET_FOLDER=/app/$MSVC_NAME/target

# Copy the JAR file generated from the previous build stage to the current working directory in the final container
COPY --from=builder $TARGET_FOLDER/msvc-orders-0.0.1-SNAPSHOT.jar .

ARG PORT_APP=8002
ENV PORT $PORT_APP

# Expose port 8001 to allow application access
EXPOSE $PORT

# Defines the input command to run the Java application
CMD ["java", "-jar", "msvc-orders-0.0.1-SNAPSHOT.jar"]
