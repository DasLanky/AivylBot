mvn clean compile package -Dmaven.test.skip=true
cd target
java -jar aivyl*.jar
