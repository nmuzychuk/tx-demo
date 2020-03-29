#!/bin/bash

ACTIVEMQ_VERSION=5.15.12

wget https://archive.apache.org/dist/activemq/${ACTIVEMQ_VERSION}/apache-activemq-${ACTIVEMQ_VERSION}-bin.tar.gz
tar -xf apache-activemq-${ACTIVEMQ_VERSION}-bin.tar.gz
./apache-activemq-${ACTIVEMQ_VERSION}/bin/activemq start

for run in {10..1}
do
  printf "%s " "${run}"
  sleep 1
done
