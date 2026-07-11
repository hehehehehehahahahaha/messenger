package com.yazikochesalna.userservice.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "authorization.service")
public record AuthServiceProperties(String url) {

}
