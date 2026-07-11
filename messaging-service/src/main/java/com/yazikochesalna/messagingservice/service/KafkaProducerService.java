package com.yazikochesalna.messagingservice.service;

import com.yazikochesalna.messagingservice.dto.events.EventDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.function.BiConsumer;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {


    private final KafkaTemplate<String, EventDTO> kafkaTemplate;

    public void sendMessage(EventDTO event, BiConsumer<SendResult<String, EventDTO>, Throwable> callback) {
        kafkaTemplate.send(KafkaTopicConstant.MESSAGES_TOPIC_NAME, event).whenComplete(callback);
    }

    public void sendMessage(EventDTO event) {
        kafkaTemplate.send(KafkaTopicConstant.MESSAGES_TOPIC_NAME, event);
    }

    public void sendEvent(EventDTO event) {
        kafkaTemplate.send(KafkaTopicConstant.EVENTS_TOPIC_NAME, event);
    }
}
