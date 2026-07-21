package org.chat.messagingweb.dto.response;

import java.time.Instant;

public record NotificationResponse(
        Long id,
        String actorUsername,
        String message,
        Long referenceId,
        Boolean isRead,
        Instant createdAt
) {
}
