package org.chat.messagingweb.controller;

import lombok.RequiredArgsConstructor;
import org.chat.messagingweb.dto.response.UserMentionResponse;
import org.chat.messagingweb.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/search")
    public ResponseEntity<List<UserMentionResponse>> searchUsers(@RequestParam("q") String query) {
        List<UserMentionResponse> users = userRepository.searchForMention(query).stream()
                .map(u -> new UserMentionResponse(u.getId(), u.getUsername()))
                .toList();

        return ResponseEntity.ok(users);
    }
}
