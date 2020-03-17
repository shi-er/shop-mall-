mvn clean package -Dmaven.test.skip
scp -r target/root.war userserver@127.0.0.1:/home/userserver/builds