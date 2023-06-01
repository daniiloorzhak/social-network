package ru.oorzhak.socialnetwork.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscriber")
public class SubscriberController {
    @Operation(summary = "Get list of subscribers")
    @GetMapping
    public ResponseEntity<List<String>> getSubscribers(){
        return null;
    }

    @Operation(summary = "Delete user from subscribers")
    @DeleteMapping("{username}")
    public ResponseEntity<?> deleteSubscriber(@PathVariable String username){
        return null;
    }
}
