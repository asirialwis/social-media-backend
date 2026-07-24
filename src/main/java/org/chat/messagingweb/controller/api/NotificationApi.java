package org.chat.messagingweb.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.chat.messagingweb.dto.response.NotificationResponse;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Notifications", description = "Operations for retrieving and updating user notifications")
@SecurityRequirement(name = "X-User-Id")
public interface NotificationApi {

    @Operation(
            summary = "Fetch unread notifications",
            description = "Retrieves a list of unread notifications for the user specified in the X-User-Id header, ordered by most recent."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved unread notifications",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = NotificationResponse.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Missing or invalid X-User-Id header",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            )
    })
    @GetMapping("/unread")
    ResponseEntity<List<NotificationResponse>> getUnreadNotifications(
            @Parameter(
                    in = ParameterIn.HEADER,
                    name = "X-User-Id",
                    description = "ID of the recipient user",
                    required = true,
                    example = "2"
            )
            @RequestHeader("X-User-Id") Long recipientId
    );

    @Operation(
            summary = "Mark notification as read",
            description = "Updates the read status of a specific notification to true. The user in X-User-Id must own the notification."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Notification successfully marked as read"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "User is not authorized to modify this notification",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Notification ID not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            )
    })
    @PatchMapping("/{id}/read")
    ResponseEntity<Void> markAsRead(
            @Parameter(description = "Notification unique ID", required = true, example = "5")
            @PathVariable("id") Long notificationId,

            @Parameter(in = ParameterIn.HEADER, name = "X-User-Id", required = true, example = "2")
            @RequestHeader("X-User-Id") Long recipientId
    );
}