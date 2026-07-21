package org.chat.messagingweb.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record CreateCommentRequest(
        @NotBlank(message = "Content cannot be blank")
        @Size(max = 2000, message = "Comment must not exceed 2000 characters")
        String content,

        Set<Long> mentionedUserIds
) { }
