#Use a base image withMaven and Java
FROM maven:3.8.3-openjdk-11
#Setup working directory inside container
WORKDIR /app
#Copy Maven project file into the container
COPY pom.xml .
COPY src ./src
#Switch to directory contain pom.xml
WORKDIR /app
#Run maven test
CMD ["mvn", "clean", "install"]