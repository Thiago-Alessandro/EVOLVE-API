package net.weg.taskmanager;

import net.weg.taskmanager.model.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class Producer {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);
    private static final String TOPIC_ERROR = "evolve-error-logs";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendErrorMessage(String title, String error, Long requestSenderId) {
        LocalDateTime dateTime = LocalDateTime.now();
        String message = String.format(
                """
                {
                title: %s;
                error: %s;
                dateTime: %s;
                requestSenderId: %d
                }
                """, title, error, dateTime, requestSenderId
        );
        logger.info(String.format("#### -> Producing message -> %s", message));
        this.kafkaTemplate.send(TOPIC_ERROR, message);
    }

}