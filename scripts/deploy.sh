#!/usr/bin/env bash
REPO=/home/ec2-user/app/stealth
DATA_DIRNAME=zip

cd $REPO/$DATA_DIRNAME

CURR_PROF=$(curl -s http://localhost/profile)

if [ "$CURR_PROF" = real1 ]; then
  NEXT_PROF=real2
  NEXT_PORT=8082
elif [ "$CURR_PROF" = real2 ]; then
  NEXT_PROF=real1
  NEXT_PORT=8081
else
  echo "> no real profiles found"
  exit 1
fi

echo "> check previous launches"
RUNNING_JARS=$(pgrep -f "\--spring.profiles.active=$NEXT_PROF")
if [ -z "$RUNNING_JARS" ]; then
  echo "nothing to kill"
else
  echo "killing running jar(s) with pid(s): $RUNNING_JARS"
  kill -15 "$RUNNING_JARS"
  sleep 5
fi

echo "> launching at $NEXT_PROF"
PROJECT_JAR=$(ls *.jar | tail -n 1)
nohup java \
  -Dspring.config.location=classpath:/application.properties,\
classpath:/application-$NEXT_PROF.properties,\
/home/ec2-user/app/application-oauth.properties,\
/home/ec2-user/app/application-db.properties\
  -jar "$PROJECT_JAR" --spring.profiles.active=$NEXT_PROF\
  > "$REPO"/nohup.out 2>&1 &

function reload_nginx() {
  echo "> switching nginx proxy port"
  sudo service nginx reload
}

echo "set \$proxy_url http://127.0.0.1:$NEXT_PORT;" | sudo tee /etc/nginx/conf.d/proxy_url.inc

sleep 10

for CNT in {1..5}
do
  RESP=$(curl -s http://localhost:$NEXT_PORT/profile)

  if [ -z "$RESP" ]; then
    echo "> waiting for the full load $CNT"
  else
    reload_nginx
    break
  fi

  if [ "$CNT" -eq 5 ]; then
    echo "too long to load, not waiting anymore"
  fi
  sleep 10
done
