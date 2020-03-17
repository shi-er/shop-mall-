mvn clean package -Dmaven.test.skip
scp -r target/root.war mq@47.98.189.110:/home/mq/builds