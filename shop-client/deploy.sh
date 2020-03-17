mvn clean package -Dmaven.test.skip
scp -r target/root.war client@47.98.189.110:/home/client/builds