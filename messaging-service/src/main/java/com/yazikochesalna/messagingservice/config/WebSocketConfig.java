package com.yazikochesalna.messagingservice.config;

import com.yazikochesalna.messagingservice.config.properties.FrontendProperties;
import com.yazikochesalna.messagingservice.handler.ChatWebSocketHandler;
import com.yazikochesalna.messagingservice.interseptor.WebSocketHandshakeInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {
    private static final String WEBSOCKET_PATH = "/api/v1/ws";
    private final ChatWebSocketHandler chatWebSocketHandler;
    private final WebSocketHandshakeInterceptor webSocketHandshakeInterceptor;
    private final FrontendProperties frontendProperties;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatWebSocketHandler, WEBSOCKET_PATH)
                .addInterceptors(webSocketHandshakeInterceptor)
                .setAllowedOrigins(frontendProperties.getOrigins().toArray(new String[0]));
    }


}
