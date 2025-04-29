#!/bin/bash

# Script to create a topic if it doesn't exist

# log function
log() {
  echo "[init-kafka] $1"
}

# Wait for Kafka to be ready to accept connections
log "Waiting for Kafka to be ready..."
sleep 10

# Topic name
TOPIC_NAME="trap-events"
# Broker address
BROKER="kafka-broker:9092"

# Controlla se il topic esiste
EXISTING_TOPICS=$(/opt/kafka/bin/kafka-topics.sh --bootstrap-server $BROKER --list)
echo "$EXISTING_TOPICS" | grep -q "^$TOPIC_NAME$"

if [ $? -eq 0 ]; then
  log "Topic '$TOPIC_NAME' already exists, no action needed."
else
  log "Topic '$TOPIC_NAME' does not exist, creating..."
  /opt/kafka/bin/kafka-topics.sh --bootstrap-server $BROKER \
    --create \
    --topic "$TOPIC_NAME" \
    --partitions 3 \
    --replication-factor 1
fi

log "Kafka initialized."