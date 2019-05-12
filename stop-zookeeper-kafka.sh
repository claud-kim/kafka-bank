#!/bin/bash

cd /Users/gimbyeongmin/git/kafka-bank/kafka_2.12-2.2.0

./bin/kafka-server-stop.sh -daemon config/server.properties
./bin/zookeeper-server-stop.sh -daemon config/server.properties

