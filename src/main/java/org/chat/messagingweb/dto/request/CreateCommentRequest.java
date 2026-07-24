package org.chat.messagingweb.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Schema(description = "Payload for creating a new comment")
public record CreateCommentRequest(
        @NotBlank(message = "Content cannot be blank")
        @Size(max = 2000, message = "Comment must not exceed 2000 characters")
        @Schema(
                description = "The raw comment text content",
                example = "Great feature! @john_doe check this out.",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Content cannot be blank")
        @Size(max = 2000, message = "Comment must not exceed 2000 characters")
        String content,

        @Schema(
                description = "Set of explicitly tagged user IDs associated with the comment",
                example = "[1, 2, 5]",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        Set<Long> mentionedUserIds
) { }
