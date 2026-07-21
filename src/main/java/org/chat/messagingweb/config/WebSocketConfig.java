package org.chat.messagingweb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {

        // Enable a simple memory-based message broker for user queues
        config.enableSimpleBroker("/queue", "/topic");

        // Prefix for messages bound for methods annotated with @MessageMapping
        config.setApplicationDestinationPrefixes("/app");

        // Prefix used for user-specific targets (e.g., /user/{username}/queue/notifications)
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        // Register the STOMP WebSocket endpoint that clients (Angular/React) connect to
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*") // Configure according to your CORS policy
                .withSockJS(); // Fallback for browsers without native WebSocket support
    }
}
