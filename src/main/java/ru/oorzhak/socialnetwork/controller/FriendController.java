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
@Tag(name = "Friend", description = "Managing friends list")
@AllArgsConstructor
public class FriendController {
    private final FriendService friendService;

    @Operation(summary = "Get friends list")
    @GetMapping
    public ResponseEntity<List<String>> getFriendList() {
        return ResponseEntity.ok(friendService.getFriendsList());
    }

    @Operation(summary = "Send friend request")
    @PostMapping("{username}")
    public ResponseEntity<?> sendFriendRequest(@PathVariable String username) {
        friendService.sendFriendRequest(username);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Accept friend request")
    @PostMapping("accept/{username}")
    public ResponseEntity<?> acceptFriendRequest(@PathVariable String username) {
        friendService.acceptFriendRequest(username);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Decline friend request")
    @PostMapping("decline/{username}")
    public ResponseEntity<?> declineFriendRequest(@PathVariable String username) {
        friendService.declineFriendRequest(username);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete user from friend list")
    @DeleteMapping("{username}")
    public ResponseEntity<?> deleteFriend(@PathVariable String username) {
        friendService.deleteFriend(username);
        return ResponseEntity.ok().build();
    }
}
