package org.chat.messagingweb.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.chat.messagingweb.controller.api.CommentApi;
import org.chat.messagingweb.domain.entity.entity.Comment;
import org.chat.messagingweb.dto.request.CreateCommentRequest;
import org.chat.messagingweb.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController implements CommentApi {

    private final CommentService commentService;
    @PostMapping
    public ResponseEntity<Long> createComment(
            @RequestHeader("X-User-Id")Long authorId,
            @Valid @RequestBody CreateCommentRequest request
            ){
        Comment comment = commentService.createComment(authorId , request);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment.getId());

    }
}
