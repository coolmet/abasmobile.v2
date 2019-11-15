ENV=$1
if [ $ENV == '' ]
then 
$ENV = "dev";
fi
java -Xms256m -Xmx512m -server -Xloggc:../logs/gc.log -verbose:gc -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=../logs -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=9034 -Dcom.sun.management.jmxremote.ssl=FALSE -Dcom.sun.management.jmxremote.authenticate=FALSE -Dlogging.config=../config/abaslogconfig.yml -Dspring.config.location=../config/abasconfig.properties,../config/abasmobileusers.yml,../config/webserverconfig.properties,../config/messages/messages.properties,../config/messages/messages_en.properties,../config/messages/messages_de.properties,../config/env/$ENV/env-config.properties -jar ../lib/gs-spring-boot-0.1.0.jar