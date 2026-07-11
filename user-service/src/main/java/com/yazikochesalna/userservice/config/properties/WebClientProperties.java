package com.yazikochesalna.userservice.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "webclient")
public record WebClientProperties (Integer timeout) {
}
