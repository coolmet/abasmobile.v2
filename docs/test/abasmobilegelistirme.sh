#!/bin/bash
SERVICE_NAME=AbasMobileGelistirme
CMD_START="java \
-Xms256m \
-Xmx512m \
-client \
-Xloggc:./logs/gc.log \
-verbose:gc -XX:+PrintGCDetails \
-XX:+HeapDumpOnOutOfMemoryError \
-XX:HeapDumpPath=./logs \
-Dcom.sun.management.jmxremote \
-Dcom.sun.management.jmxremote.port=9934 \
-Dcom.sun.management.jmxremote.ssl=FALSE \
-Dcom.sun.management.jmxremote.authenticate=FALSE \
-Dlog4j.configuration=./config/abaslogconfig.yml \
-Dspring.profiles.active=prod \
-jar ./lib/abasmobile-0.0.1-SNAPSHOT.jar \
--spring.config.location=file:../config/abasconfig.properties,./config/"
JAVA_HOME=/usr/java8
USER_DIR=~
CURRENT_DIR=$(pwd)
CMD_STOP=$CMD_START
USER_NAME=$USER
#USER_NAME=s3
cd /u/abasmobile
##
usage() 
   { 
	for i in {16..21} {21..16} {16..21} {21..16} {16..21} {21..16} ; do echo -en "\e[38;5;${i}m_\e[0m" ; done ; echo
	echo "$SERVICE_NAME Service Usage:" 
	echo -e "Start the service with parameter: \e[41m\e[93m\e[4m\e[1m\e[5mstart\e[0m\e[22m\e[24m\e[39m\e[49m"
	echo -e "Stop the service with parameter: \e[41m\e[93m\e[4m\e[1m\e[5mstop\e[0m\e[22m\e[24m\e[39m\e[49m"
	echo -e "Restart the service with parameter: \e[41m\e[93m\e[4m\e[1m\e[5mrestart\e[0m\e[22m\e[24m\e[39m\e[49m"
	echo -e "View status of service with parameter: \e[41m\e[93m\e[4m\e[1m\e[5mstatus\e[0m\e[22m\e[24m\e[39m\e[49m"
	for i in {16..21} {21..16} {16..21} {21..16} {16..21} {21..16} ; do echo -en "\e[38;5;${i}m_\e[0m" ; done ; echo
	} 

start()
	{
		export TERM=vt100
		echo "Starting $SERVICE_NAME ..."
        if [ -z "$(pgrep -l -f $SERVICE_NAME | awk '{print $1}')" ]; then
		    if [ "$USER_NAME" == "$USER" ]; then
				sh -c "exec -a $SERVICE_NAME $CMD_START"
			else
				su - $USER_NAME -c "exec -a $SERVICE_NAME $CMD_START"
			fi            
            echo "$SERVICE_NAME started ..."
        else
            echo "$SERVICE_NAME is already running ..."
        fi
	}
	
stop()
	{
		export TERM=vt100
		if ! [ -z "$(pgrep -l -f $SERVICE_NAME | awk '{print $1}')" ]; then
            PID=$(pgrep -l -f $SERVICE_NAME | awk '{print $1}');
            echo "$SERVICE_NAME stoping ..."
				if [ "$CMD_START" == "$CMD_STOP" ]; then
					echo -n ""
				else
					if [ "$USER_NAME" == "$USER" ]; then
						sh -c "exec -a $SERVICE_NAME $CMD_STOP"
					else
						su - $USER_NAME -c "exec -a $SERVICE_NAME $CMD_STOP"
					fi 				    
					echo -n ""
				fi
            kill $PID;
            echo "$SERVICE_NAME stopped ..."
        else
            echo "$SERVICE_NAME is not running ..."
        fi
	}

# if less than two arguments supplied, display usage 
if [  $# -le 0 ] 
	then 
		usage
		exit 1
fi 
# check whether user had supplied -h or --help . If yes display usage 
if [[  "$1" == "--help" ||  "$1" == "-h" ]]; then 
		usage
		exit 0
fi 
#############################################
case $1 in
    start)  start ;;
    stop)   stop ;;
    restart) stop; start ;;
    status)
        echo "Checking $SERVICE_NAME service status..."
        if [ -z "$(pgrep -l -f $SERVICE_NAME | awk '{print $1}')" ]; then
            echo "$SERVICE_NAME service is not running ..."
        else
            echo "$SERVICE_NAME service is already running ..."
        fi
    ;;
	*)  # No more options
	      echo "Unknown option : $1"
		  usage
          exit 1 
          ;;
esac
#

exit
#su - s3 -c "exec -a TEST mandwahl.sh"
#pgrep -l -f TEST
#pgrep -l -f TEST | awk '{print $1}'
#ps -aux | grep TEST
#ps aux | grep TEST | grep -v grep | awk '{print $2}'
#runuser -l s3 -c 'echo $PWD'
#*/5 * * * * /u/abas/demo/abaspdm.sh start >> ./win/tmp/XPDM.BATCH."`date +\%Y\%m\%d\%H`".TMP 2>&1