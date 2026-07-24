package org.chat.messagingweb.dto.response;

import org.chat.messagingweb.domain.entity.entity.Notification;

public record NotificationResponse(
        Long id,
        String actorUsername,
        String message,
        Long referenceId,
        Boolean isRead,
        java.time.LocalDateTime createdAt
) {
    public static NotificationResponse fromEntity(Notification entity) {
        // Construct custom message based on notification type if needed
        String displayMessage = "@" + entity.getActor().getUsername() + " mentioned you in a comment";

        return new NotificationResponse(
                entity.getId(),
                entity.getActor().getUsername(),
                displayMessage,
                entity.getReferenceId(),
                entity.getIsRead(),
                entity.getCreatedAt()
        );
    }
}
