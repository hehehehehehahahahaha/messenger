package com.yazikochesalna.userservice.config;

import com.yazikochesalna.userservice.config.properties.AuthServiceProperties;
import com.yazikochesalna.userservice.config.properties.MessagingServiceProperties;
import com.yazikochesalna.userservice.config.properties.WebClientProperties;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
@EnableConfigurationProperties({
        AuthServiceProperties.class,
        MessagingServiceProperties.class,
        WebClientProperties.class
})
@RequiredArgsConstructor
@Configuration
public class WebClientConfig {

    private final AuthServiceProperties authServiceProperties;
    private final MessagingServiceProperties messagingServiceProperties;
    private final WebClientProperties webClientProperties;

    private final static int timeoutSeconds = 5;

    @Bean
    public WebClient.Builder webClientBuilder() {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, webClientProperties.timeout())
                .responseTimeout(Duration.ofSeconds(timeoutSeconds))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(timeoutSeconds, TimeUnit.SECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(timeoutSeconds, TimeUnit.SECONDS)));

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient));
    }

    @Bean
    public WebClient authServiceWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl(authServiceProperties.url())
                .build();
    }

    @Bean
    public WebClient messagingServiceWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl(messagingServiceProperties.url())
                .build();
    }
}