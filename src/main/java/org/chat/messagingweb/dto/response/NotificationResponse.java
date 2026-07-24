package org.chat.messagingweb.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.chat.messagingweb.domain.entity.entity.Notification;

@Schema(description = "Response payload representing a user notification")
public record NotificationResponse(
        @Schema(description = "Notification ID", example = "105")
        Long id,

        @Schema(description = "Username of the user who triggered the event", example = "john_doe")
        String actorUsername,

        @Schema(description = "Human-readable message for the notification", example = "@john_doe mentioned you in a comment")
        String message,

        @Schema(description = "ID of the associated resource (e.g., comment ID)", example = "42")
        Long referenceId,

        @Schema(description = "Read status flag", example = "false")
        Boolean isRead,

        @Schema(description = "Timestamp when notification was created", example = "2026-07-24T17:30:00")
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
