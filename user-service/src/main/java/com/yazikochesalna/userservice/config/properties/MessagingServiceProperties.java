package com.yazikochesalna.userservice.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "messaging.service")
public record MessagingServiceProperties(String url) {

}
