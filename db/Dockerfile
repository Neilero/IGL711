FROM maven:3.6.1-jdk-11

COPY . /project
RUN  cd /project && mvn -B --no-transfer-progress clean install
RUN mv /project/target/dbinterface-*.jar /project/target/dbinterface.jar

ENTRYPOINT ["java","-jar","project/target/dbinterface.jar"]