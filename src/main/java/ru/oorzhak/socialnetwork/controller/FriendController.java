package ru.oorzhak.socialnetwork.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friend")
@Tag(name = "", description = "")
public class FriendController {
    @Operation(summary = "Get friends list")
    @GetMapping
    public ResponseEntity<List<?>> getFriends(){
        return null;
    }

    @Operation(summary = "Send friend request")
    @PostMapping("{username}")
    public ResponseEntity<?> sendFriendRequest(@PathVariable String username){
        return null;
    }

    @Operation(summary = "Delete friend")
    @DeleteMapping("{username}")
    public ResponseEntity<?> deleteFriend(@PathVariable String username){
        return null;
    }
}
