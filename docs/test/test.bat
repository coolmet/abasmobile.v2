echo off
set ENV=%1
IF [%1]==[] (
set ENV=dev
)
java ^
-Xms256m ^
-Xmx512m ^
-server ^
-Xloggc:./logs/gc.log ^
-verbose:gc -XX:+PrintGCDetails ^
-XX:+HeapDumpOnOutOfMemoryError ^
-XX:HeapDumpPath=./logs ^
-Dcom.sun.management.jmxremote ^
-Dcom.sun.management.jmxremote.port=9934 ^
-Dcom.sun.management.jmxremote.ssl=FALSE ^
-Dcom.sun.management.jmxremote.authenticate=FALSE ^
-Dlog4j.configuration=./config/abaslogconfig.yml ^
-Dspring.profiles.active=prod ^
-jar ../target/abasmobile-0.0.1-SNAPSHOT.jar ^
--spring.config.location=file:../config/abasconfig.properties,./config/
