package ru.oorzhak.socialnetwork.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.oorzhak.socialnetwork.dto.MessageDTO;
import ru.oorzhak.socialnetwork.dto.MessageResponseDTO;
import ru.oorzhak.socialnetwork.service.MessageService;

import java.util.List;

@RestController
@RequestMapping("/message")
@AllArgsConstructor
@Tag(name = "Message", description = "")
public class MessageController {
    private final MessageService messageService;
    @GetMapping
    public ResponseEntity<List<String>> getUsersWithMessageHistory() {
        return ResponseEntity.ok(messageService.getUsersWithMessageHistory());
    }

    @GetMapping("{username}")
    public ResponseEntity<List<MessageResponseDTO>> getUserMessageHistory(@PathVariable String username) {
        return ResponseEntity.ok(messageService.getMessageHistory(username));
    }

    @PostMapping("{username}")
    public ResponseEntity<?> sendMessageToUser(@PathVariable String username, @RequestBody @NotBlank MessageDTO messageDTO){
        return ResponseEntity.ok(messageService.sendMessage(username, messageDTO));
    }
}
