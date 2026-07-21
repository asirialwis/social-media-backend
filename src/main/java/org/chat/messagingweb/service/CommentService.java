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

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    @Transactional
    public Comment createComment(Long authorId, CreateCommentRequest request) {
        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with ID: " + authorId));

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
