#!/bin/bash

cd /Users/gimbyeongmin/git/kafka-bank/kafka_2.12-2.2.0

./bin/kafka-topics.sh --zookeeper localhost:2181 --force --delete --topic test

# ./bin/kafka-topics.sh --zookeeper localhost:2181 --force --delete --topic __consumer_offsets

./bin/zookeeper-shell.sh localhost:2181 <<< "rmr /brokers/topics"
./bin/kafka-topics.sh --zookeeper localhost:2181 --list

./bin/kafka-server-stop.sh -daemon config/server.properties
./bin/zookeeper-server-stop.sh -daemon config/server.properties


rm -rf /tmp/zookeeper/
rm -rf /tmp/kafka-logs/
