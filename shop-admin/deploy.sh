mvn clean package -Dmaven.test.skip
scp -r target/root.war shopadmin@127.0.0.1:/home/shopadmin/builds