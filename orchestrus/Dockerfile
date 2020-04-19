FROM maven:3.6.1-jdk-11

COPY . /project
RUN  cd /project && mvn -B --no-transfer-progress clean install
RUN mv /project/target/orchestrus-*.jar /project/target/orchestrus.jar

ENTRYPOINT ["java","-jar","project/target/orchestrus.jar"]