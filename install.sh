#!/bin/bash


wget http://apache.mirror.cdnetworks.com/kafka/2.2.0/kafka_2.12-2.2.0.tgz
tar xvfz kafka_2.12-2.2.0.tgz

cp -rf config kafka_2.12-2.2.0/

rm -rf kafka_2.12-2.2.0.tgz

echo "down load and rm -rf " 
