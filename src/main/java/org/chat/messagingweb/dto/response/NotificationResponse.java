package org.chat.messagingweb.dto.response;

public record NotificationResponse(
        Long id,
        String actorUsername,
        String message,
        Long referenceId,
        Boolean isRead,
        java.time.LocalDateTime createdAt
) {
}
