package com.soubhagya.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * WebSocketConfig is a configuration class that sets up the WebSocket message broker
 * using the Spring framework. It implements the WebSocketMessageBrokerConfigurer
 * interface to customize how WebSocket message handling is configured.
 *
 * This class is annotated with @Configuration and
 * @EnableWebSocketMessageBroker, indicating that it is a source of
 * bean definitions and that WebSocket message handling is enabled.
 *
 * The configureMessageBroker method configures the message broker, setting
 * up a simple in-memory broker with a specified destination prefix for
 * messages originating from the client with "/app".
 *
 * The registerStompEndpoints method registers STOMP endpoints that clients can
 * connect to. It also specifies allowed origins and enables SockJS as a fallback
 * for browsers that don’t support WebSocket.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    /**
     * Configures the message broker for handling WebSocket messages.
     *
     * This method sets up a simple in-memory message broker with a specified
     * destination prefix for messages coming from the client.
     *
     * @param config the MessageBrokerRegistry used to configure the message broker
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    /**
     * Registers STOMP endpoints for WebSocket communication, allowing the specified
     * origins to establish connections and enabling SockJS fallback options.
     *
     * @param registry the registry to which STOMP endpoints are added
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat")
                .setAllowedOrigins("http://localhost:5173")
                .withSockJS();
    }
}
