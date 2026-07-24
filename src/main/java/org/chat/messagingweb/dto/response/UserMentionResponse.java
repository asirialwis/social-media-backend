package org.chat.messagingweb.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Summary payload representing a mentioned user")
public record UserMentionResponse(

        @Schema(description = "Unique user ID", example = "1")
        Long id ,

        @Schema(description = "Username of the mentioned user", example = "john_doe")
        String username) {
}
