package com.yazikochesalna.userservice.service.externalservice;

import com.yazikochesalna.common.service.JwtService;
import com.yazikochesalna.userservice.config.properties.WebClientProperties;
import com.yazikochesalna.userservice.dto.notificationdto.NotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.naming.ServiceUnavailableException;
import java.time.Duration;

@Service
public class MessagingClientService {

    private static final String BEARER_TOKEN_PREFIX = "Bearer ";
    private final JwtService jwtService;
    private final WebClient messagingWebClient;

    public MessagingClientService(
            JwtService jwtService, @Qualifier("messagingServiceWebClient") WebClient messagingWebClient, WebClientProperties webClientProperties) {
        this.jwtService = jwtService;
        this.messagingWebClient = messagingWebClient;
        this.webClientProperties = webClientProperties;
    }

    private final static String AVATAR_MESSAGING_URL = "/api/v1/ws/notification/new-user-avatar";

    private final static String USERNAME_MESSAGING_URL = "/api/v1/ws/notification/new-username";

    private final WebClientProperties webClientProperties;

    public void setNewAvatar(NotificationDto notificationDTO) throws ServiceUnavailableException {
        messagingWebClient.post()
                .uri(AVATAR_MESSAGING_URL)
                .header(HttpHeaders.AUTHORIZATION, BEARER_TOKEN_PREFIX + jwtService.generateServiceToken())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(notificationDTO)
                .retrieve()
                .toBodilessEntity()
                .timeout(Duration.ofSeconds(webClientProperties.timeout()))
                .blockOptional(Duration.ofSeconds(webClientProperties.timeout()))
                .orElseThrow(() -> new ServiceUnavailableException("Failed to send avatar notification"));
    }

    public void setNewUsername(NotificationDto notificationDTO) throws ServiceUnavailableException {
        messagingWebClient.post()
                .uri(USERNAME_MESSAGING_URL)
                .header(HttpHeaders.AUTHORIZATION, BEARER_TOKEN_PREFIX + jwtService.generateServiceToken())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(notificationDTO)
                .retrieve()
                .toBodilessEntity()
                .timeout(Duration.ofSeconds(webClientProperties.timeout()))
                .blockOptional(Duration.ofSeconds(webClientProperties.timeout()))
                .orElseThrow(() -> new ServiceUnavailableException("Failed to send avatar notification"));
    }

}
