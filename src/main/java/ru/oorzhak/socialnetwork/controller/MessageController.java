package ru.oorzhak.socialnetwork.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.oorzhak.socialnetwork.dto.MessageSendDTO;
import ru.oorzhak.socialnetwork.dto.MessageDetailsDTO;
import ru.oorzhak.socialnetwork.service.MessageService;

import java.util.List;

@RestController
@RequestMapping("/message")
@AllArgsConstructor
@Tag(name = "Message", description = "Common messaging operations")
public class MessageController {
    private final MessageService messageService;
    @GetMapping
    public ResponseEntity<List<String>> getUsersWithMessageHistory() {
        return ResponseEntity.ok(messageService.getUsersWithMessageHistory());
    }

    @GetMapping("{username}")
    public ResponseEntity<List<MessageDetailsDTO>> getUserMessageHistory(@PathVariable String username) {
        return ResponseEntity.ok(messageService.getMessageHistory(username));
    }

    @PostMapping("{username}")
    public ResponseEntity<?> sendMessageToUser(@PathVariable String username, @RequestBody @NotBlank MessageSendDTO messageSendDTO){
        return ResponseEntity.ok(messageService.sendMessage(username, messageSendDTO));
    }
}
