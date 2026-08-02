package org.chat.messagingweb.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.chat.messagingweb.domain.entity.entity.Comment;
import org.chat.messagingweb.domain.entity.entity.User;
import org.chat.messagingweb.dto.request.CreateCommentRequest;
import org.chat.messagingweb.exception.ResourceNotFoundException;
import org.chat.messagingweb.repository.CommentRepository;
import org.chat.messagingweb.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    @Transactional
    public Comment createComment(String keycloakUserIdStr,String username , String email, CreateCommentRequest request) {
        User author = userRepository.findByKeycloakId(keycloakUserIdStr)
                .orElseGet(() -> userRepository.save(User.builder()
                        .keycloakId(keycloakUserIdStr)
                        .username(username != null ? username : keycloakUserIdStr)
                        .email(email != null ? email : keycloakUserIdStr + "@noemail.com")
                        .build()));

        Comment comment = Comment.builder()
                .content(request.content())
                .author(author)
                .build();

        Comment savedComment = commentRepository.save(comment);

        // Async dispatch keeps comment creation fast
        notificationService.processAndSendTagNotification(
                author,
                request.mentionedUserIds(),
                savedComment.getId()
        );

        return savedComment;
    }
}
