package org.chat.messagingweb.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.chat.messagingweb.controller.api.CommentApi;
import org.chat.messagingweb.domain.entity.entity.Comment;
import org.chat.messagingweb.dto.request.CreateCommentRequest;
import org.chat.messagingweb.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController implements CommentApi {

    private final CommentService commentService;

    @PostMapping
    @Override
    public ResponseEntity<Long> createComment(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody CreateCommentRequest request
    )
    {
        if (jwt == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User authentication required");
        }
        String keycloakUserIdStr = jwt.getSubject();
        String username = jwt.getClaimAsString("preferred_username");
        String email = jwt.getClaimAsString("email");

        System.out.println(keycloakUserIdStr);
        Comment comment = commentService.createComment(keycloakUserIdStr,username,email , request);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment.getId());

    }



    private Long extractAuthorId(Jwt jwt) {
        // Fallback check: try "user_id" claim first, then standard "sub" claim
        String userIdStr = jwt.getClaimAsString("user_id");
        if (userIdStr == null) {
            userIdStr = jwt.getSubject();
        }

        if (userIdStr == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User ID missing from JWT token");
        }

        try {
            return Long.parseLong(userIdStr);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user ID format in token");
        }


    }
}
