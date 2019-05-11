#!/bin/bash

cd /Users/gimbyeongmin/git/kafka-bank/kafka_2.12-2.2.0

PART=5
TOPIC="test"

./bin/kafka-topics.sh config/server.properties --create --bootstrap-server localhost:9092 --replication-factor 1 \
--partitions $PART --topic $TOPIC

./bin/kafka-topics.sh config/server.properties --bootstrap-server localhost:9092 --list 

