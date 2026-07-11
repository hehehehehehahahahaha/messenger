package com.yazikochesalna.userservice.service.externalservice;

import com.yazikochesalna.common.service.JwtService;
import com.yazikochesalna.userservice.config.properties.WebClientProperties;
import com.yazikochesalna.userservice.dto.personalprofiledto.AuthLoginDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.naming.ServiceUnavailableException;
import java.time.Duration;

@Service
public class AuthorizationClientService {

    private static final String BEARER_TOKEN_PREFIX = "Bearer ";
    private final JwtService jwtService;
    private final WebClient authServiceWebClient;

    public AuthorizationClientService(
            JwtService jwtService, @Qualifier("authServiceWebClient") WebClient authServiceWebClient, WebClientProperties properties) {
        this.jwtService = jwtService;
        this.authServiceWebClient = authServiceWebClient;
        this.webClientProperties = properties;
    }

    private final static String AUTH_URL = "/api/v1/auth/getlogin?userID={userId}";

    private final WebClientProperties webClientProperties;

    public String getUserLogin(long userId) throws ServiceUnavailableException {
        return authServiceWebClient.get()
                .uri(AUTH_URL, userId)
                .header(HttpHeaders.AUTHORIZATION, BEARER_TOKEN_PREFIX + jwtService.generateServiceToken())
                .retrieve()
                .bodyToMono(AuthLoginDto.class)
                .timeout(Duration.ofSeconds(webClientProperties.timeout()))
                .blockOptional(Duration.ofSeconds(webClientProperties.timeout()))
                .orElseThrow(() -> new ServiceUnavailableException("Failed to get login"))
                .getLogin();
    }
}
