package ru.oorzhak.socialnetwork.controller;

import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {
    @GetMapping
    public ResponseEntity<List<String>> getUsersWithMessageHistory() {
        return null;
    }

    @GetMapping("{username}")
    public ResponseEntity<List<?>> getUserMessageHistory(@PathVariable String username) {
        return null;
    }

    @PostMapping("{username}")
    public ResponseEntity<?> sendMessageToUser(@RequestBody @NotBlank String message, @PathVariable String username){
        return null;
    }
}
