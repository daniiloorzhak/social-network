package ru.oorzhak.socialnetwork.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.oorzhak.socialnetwork.service.FollowerService;

import java.util.List;

@RestController
@RequestMapping("/follower")
@AllArgsConstructor
@Tag(name = "Follower", description = "")
public class FollowerController {
    private final FollowerService followerService;
    @Operation(summary = "Get list of followers")
    @GetMapping
    public ResponseEntity<List<String>> getFollowersUsernames(){
        return ResponseEntity.ok(followerService.getFollowersUsernamesList());
    }
}
