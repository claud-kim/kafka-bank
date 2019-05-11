#!/bin/bash

cd /Users/gimbyeongmin/git/kafka-bank/kafka_2.12-2.2.0/
./bin/zookeeper-server-start.sh -daemon config/zookeeper.properties
sleep 3
./bin/kafka-server-start.sh -daemon config/server.properties
sleep 3
jps
