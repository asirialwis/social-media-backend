package org.chat.messagingweb.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.chat.messagingweb.domain.entity.entity.Notification;
import org.chat.messagingweb.domain.entity.entity.User;
import org.chat.messagingweb.domain.enums.NotificationType;
import org.chat.messagingweb.dto.response.NotificationResponse;
import org.chat.messagingweb.repository.NotificationRepository;
import org.chat.messagingweb.repository.UserRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Async
    @Transactional
    public void processAndSendTagNotification(User actor , Set<Long> recipientIds, Long commentId){
        if(recipientIds == null || recipientIds.isEmpty()) return;

        List<User> recipients = userRepository.findAllById(recipientIds);

        for(User recipient : recipients){
            if(recipient.getId().equals(actor.getId())) continue;

            Notification notification = Notification.builder()
                    .actor(actor)
                    .recipient(recipient)
                    .type(NotificationType.TAGGED_IN_COMMENT)
                    .referenceId(commentId)
                    .isRead(false)
                    .build();

            Notification saved = notificationRepository.save(notification);

            NotificationResponse payload = new NotificationResponse(
                    saved.getId(),
                    actor.getUsername(),
                    actor.getUsername() + "tagged you in a comment",
                    commentId,
                    false,
                    saved.getCreatedAt()
            );
            // STOMP WebSocket Push
            messagingTemplate.convertAndSendToUser(
                    recipient.getUsername(),
                    "/queue/notifications",
                    payload
            );
            log.info("Successfully pushed notification to user: {}", recipient.getUsername());
        }
    }

}
