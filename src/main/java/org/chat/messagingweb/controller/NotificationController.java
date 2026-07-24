package org.chat.messagingweb.controller;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.chat.messagingweb.controller.api.NotificationApi;
import org.chat.messagingweb.dto.response.NotificationResponse;
import org.chat.messagingweb.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController implements NotificationApi {
    private final NotificationService notificationService;

    @GetMapping("/unread")
    public ResponseEntity<List<NotificationResponse>> getUnreadNotifications(
            @RequestHeader("X-User-Id") Long recipientId) {

        List<NotificationResponse> unread = notificationService.getUnreadNotifications(recipientId);
        return ResponseEntity.ok(unread);
    }
    @PatchMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(
            @PathVariable("id") Long notificationId,
            @RequestHeader("X-User-Id") Long recipientId) {

        notificationService.markAsRead(notificationId, recipientId);
        return ResponseEntity.noContent().build();
    }

}
