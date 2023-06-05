package ru.oorzhak.socialnetwork.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.oorzhak.socialnetwork.service.FollowerService;

import java.util.List;

@RestController
@RequestMapping("/subscriber")
@AllArgsConstructor
@Tag(name = "Follower")
public class FollowerController {
    private final FollowerService followerService;
    @Operation(summary = "Get list of subscribers")
    @GetMapping
    public ResponseEntity<List<String>> getSubscribers(){
        return ResponseEntity.ok(followerService.getSubscribersList());
    }

    @Operation(summary = "Delete user from subscribers")
    @DeleteMapping("{username}")
    public ResponseEntity<?> deleteSubscriber(@PathVariable String username){
//        return ResponseEntity.ok(subscriberService.deleteSubscriber(username));
        return null;
    }
}
