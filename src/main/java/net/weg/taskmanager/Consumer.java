package net.weg.taskmanager;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.util.*;

@Service
public class Consumer {

    private final Logger logger = LoggerFactory.getLogger(Producer.class);

    @KafkaListener(topics = "evolve-error-logs", groupId = "group_id")
    public void consume(String message) throws IOException {
        logger.info(String.format("#### -> Consumed message -> %s", message));
    }

    public Collection<String> getAllLogs(){

        Properties consumerProperties = new Properties();
        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
        consumerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProperties);
        consumer.subscribe(List.of("evolve-error-logs"));

        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(200));

        Collection<String> logs = new HashSet<>();
        for (ConsumerRecord<String, String> record : records) {
//            logger.info(record.value());
            logs.add(record.value());
        }
        consumer.close();
        System.out.println(logs);
        return logs;
    }


}
