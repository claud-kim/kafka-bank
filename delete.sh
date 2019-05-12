#!/bin/bash
echo "Enter userName of topic to empty:"
read topicName

# env set
PART=5
Kafka_Home=/Users/gimbyeongmin/git/kafka-bank/kafka_2.12-2.2.0

# run cmd 
$Kafka_Home/bin/kafka-configs.sh --zookeeper localhost:2181 --alter --entity-type topics --entity-userName $topicName --add-config retention.ms=1000
sleep 5


$Kafka_Home/bin/zookeeper-shell.sh localhost:2181 <<EOF
rmr /brokers/topics/$topicName
ls /brokers/topics
quit
EOF

$Kafka_Home/bin/kafka-configs.sh --zookeeper localhost:2181 --alter --entity-type topics --entity-userName $topicName --delete-config retention.ms



#$Kafka_Home/bin/kafka-topics.sh config/server.properties --create --bootstrap-server localhost:9092 --replication-factor 1 \
#--partitions $PART --topic $topicName

sleep 5

#$Kafka_Home/bin/kafka-topics.sh config/server.properties --bootstrap-server localhost:9092 --list


