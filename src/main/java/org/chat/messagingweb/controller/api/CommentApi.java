package org.chat.messagingweb.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.chat.messagingweb.dto.request.CreateCommentRequest;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Tag(name = "Comments", description = "Operations for posting comments and triggering user mention notifications")
@SecurityRequirement(name = "X-User-Id")
public interface CommentApi {

    @Operation(
            summary = "Create a new comment",
            description = "Creates a comment containing text and optional user IDs to mention. Automatically triggers notifications for mentioned users."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Comment created and mentions processed successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid payload or missing header",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Author or mentioned user ID not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            )
    })
    @PostMapping
    ResponseEntity<Long> createComment(
            @Parameter(in = ParameterIn.HEADER, name = "X-User-Id", required = true, example = "1")
            @RequestHeader("X-User-Id") Long authorId,

            @Valid @RequestBody CreateCommentRequest request
    );
}