package org.chat.messagingweb.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.chat.messagingweb.dto.response.UserMentionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Users", description = "User lookups for @mention suggestions")
public interface UserApi {

    @Operation(
            summary = "Search users for mentions",
            description = "Search matching users by username prefix for typeahead mention components."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Matching users list",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserMentionResponse.class))
                    )
            )
    })
    @GetMapping("/search")
    ResponseEntity<List<UserMentionResponse>> searchUsers(
            @Parameter(description = "Username prefix search query", required = true, example = "john")
            @RequestParam("query") String query
    );
}