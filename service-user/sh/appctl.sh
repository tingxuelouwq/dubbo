#!/bin/sh

# java env
export JAVA_HOME=$JAVA_HOME

# servcie
SERVICE_DIR=/home/kevin/applications/service/user
SERVICE_NAME=service-user
JAR_NAME=$SERVICE_NAME\.jar
PID=$SERVICE_NAME\.pid

cd $SERVICE_DIR
 
case "$1" in
    start)
        nohup $JAVA_HOME/bin/java -Xms256m -Xmx512m -jar $JAR_NAME >/dev/null 2>&1 &
        echo $! > $SERVICE_DIR/$PID
        echo -n "Starting $SERVICE_NAME.. "
        sleep 3 
        P_ID=$(ps -ef | grep -w "$SERVICE_NAME" | grep -v "grep" | awk '{print $2}')
        if [ -n "$P_ID" ]; then
            echo "SUCCESS! ($P_ID)"
        else
            echo "FAILED!"
        fi
        ;;
    stop)
        kill $(cat $SERVICE_DIR/$PID)
        rm -rf $SERVICE_DIR/$PID
        echo -n "Shutting down $SERVICE_NAME.. "

        sleep 3
        P_ID=$(ps -ef | grep -w "$SERVICE_NAME" | grep -v "grep" | awk '{print $2}')
        if [ -z "$P_ID" ]; then
            echo "SUCCESS!"
        else
            echo "FAILED!"
            echo "Killing $SERVICE_NAME ($P_ID).. SUCCESS!"
            kill -9 $P_ID
        fi
        ;;
    restart)
        $0 stop
        sleep 2
        $0 start
        ;;
    status)
        P_ID=$(ps -ef | grep -w "$SERVICE_NAME" | grep -v "grep" | awk '{print $2}')
        if [ -n "$P_ID" ]; then
            echo "SUCCESS! $SERVICE_NAME running ($P_ID)"
        else
            echo "ERROR! $SERVICE_NAME is not running"
        fi
        ;;
    *)
        echo "usage: start| stop| restart"
        ;;
esac
exit 0
