package com.yazikochesalna.messagingservice.service;

import com.yazikochesalna.messagingservice.dto.events.EventDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final WebSocketEventService webSocketEventService;

    @KafkaListener(topics = KafkaTopicConstant.MESSAGES_TOPIC_NAME)
    public void listenMessage(EventDTO event, Acknowledgment ack) {
        webSocketEventService.broadcastMessageToParticipants(event);
        ack.acknowledge();
    }

    @KafkaListener(topics = KafkaTopicConstant.EVENTS_TOPIC_NAME)
    public void listenEvent(EventDTO event, Acknowledgment ack) {
        webSocketEventService.broadcastEventToParticipants(event);
        ack.acknowledge();
    }

}
