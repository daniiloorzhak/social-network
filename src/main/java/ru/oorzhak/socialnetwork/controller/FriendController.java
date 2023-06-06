package ru.oorzhak.socialnetwork.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.oorzhak.socialnetwork.service.FriendService;

import java.util.List;

@RestController
@RequestMapping("/friend")
@AllArgsConstructor
@Tag(name = "Friend", description = "Managing friends list")
public class FriendController {
    private final FriendService friendService;

    @Operation(summary = "Get list of friends")
    @GetMapping
    public ResponseEntity<List<String>> getFriendList() {
        return ResponseEntity.ok(friendService.getFriendsList());
    }

    @Operation(summary = "Get list of friend requests")
    @GetMapping("/requests")
    public ResponseEntity<List<String>> getFriendRequestsUsernames() {
        return ResponseEntity.ok(friendService.getFriendRequestsUsernames());
    }

    @Operation(summary = "Send friend request")
    @PostMapping("{username}")
    public ResponseEntity<String> sendFriendRequest(@PathVariable String username) {
        return ResponseEntity.ok(friendService.sendFriendRequest(username));
    }

    @Operation(summary = "Accept friend request")
    @PostMapping("accept/{username}")
    public ResponseEntity<String> acceptFriendRequest(@PathVariable String username) {
        return ResponseEntity.ok(friendService.acceptFriendRequest(username));
    }

    @Operation(summary = "Decline friend request")
    @PostMapping("decline/{username}")
    public ResponseEntity<String> declineFriendRequest(@PathVariable String username) {
        return ResponseEntity.ok(friendService.declineFriendRequest(username));
    }

    @Operation(summary = "Delete user from friend list")
    @DeleteMapping("{username}")
    public ResponseEntity<String> deleteFriend(@PathVariable String username) {
        return ResponseEntity.ok(friendService.deleteFriend(username));
    }
}

