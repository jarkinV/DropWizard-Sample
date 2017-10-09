
package com.softserve.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.softserve.domain.Record;

@Service
public class KafkaUtil {

    @Value("${kafka.bootstrap.servers}")
    private String bootstrapServers;
    @Value("${kafka.key.serializer}")
    private String keySerializer;
    @Value("${kafka.value.serializer}")
    private String valueSerializer;
    @Value("${kafka.acks}")
    private String acks;
    @Value("${kafka.retries}")
    private String retries;
    @Value("${kafka.linger.ms}")
    private String lingerMs;

    public void sendMessage(String msg) {
        Producer<String, String> producer = getProducer();
        ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>("first_topic", "3", msg);
        producer.send(producerRecord);
        producer.flush();
        producer.close();
    }

    public java.util.List<Record> getMessage() {
        Consumer<String, String> consumer = getConsumer();
        final int giveUp = 100;
        int noRecordsCount = 0;
        java.util.List<Record> records = new ArrayList<>();
        while (true) {
            final ConsumerRecords<String, String> consumerRecords = consumer.poll(1000);

            if (consumerRecords.count() == 0) {
                noRecordsCount++;
                if (noRecordsCount > giveUp)
                    break;
                else
                    continue;
            }

            consumerRecords.forEach(record -> {
                records.add(new Record(record.key(), record.value(), record.partition(), record.offset()));
                });
            consumer.commitAsync();
        }
        consumer.close();
        return records;
    }

    private Consumer<String, String> getConsumer() {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", bootstrapServers);
        properties.setProperty("key.serializer", keySerializer);
        properties.setProperty("value.serializer", valueSerializer);
        properties.setProperty("acks", acks);
        properties.setProperty("retries", retries);
        Consumer<String, String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singletonList("first_topic"));
        return consumer;

    }

    private Producer<String, String> getProducer() {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", bootstrapServers);
        properties.setProperty("key.serializer", keySerializer);
        properties.setProperty("value.serializer", valueSerializer);
        properties.setProperty("acks", acks);
        properties.setProperty("retries", retries);
        Producer<String, String> producer = new KafkaProducer<String, String>(properties);
        return producer;
    }
}
