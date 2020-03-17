mvn clean package -Dmaven.test.skip
scp -r target/root.war goods@47.98.189.110:/home/goods/builds