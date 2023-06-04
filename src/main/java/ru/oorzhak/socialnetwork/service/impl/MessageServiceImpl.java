package ru.oorzhak.socialnetwork.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.oorzhak.socialnetwork.dto.MessageDTO;
import ru.oorzhak.socialnetwork.dto.MessageResponseDTO;
import ru.oorzhak.socialnetwork.exception.UserWithUsernameNotFound;
import ru.oorzhak.socialnetwork.exception.UserWithUsernameNotFriend;
import ru.oorzhak.socialnetwork.model.Message;
import ru.oorzhak.socialnetwork.model.User;
import ru.oorzhak.socialnetwork.repository.MessageRepository;
import ru.oorzhak.socialnetwork.repository.UserRepository;
import ru.oorzhak.socialnetwork.service.MessageService;

import java.util.List;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    @Override
    @Transactional
    public Message sendMessage(String toUserUsername, MessageDTO messageDTO) {
        User to = userRepository
                .findByUsername(toUserUsername)
                .orElseThrow(() -> new UserWithUsernameNotFound(toUserUsername));
        User from = userRepository
                .findByUsername(getCurrentUserUsername())
                .orElseThrow();
        if (!from.getFriends().contains(to))
            throw new UserWithUsernameNotFriend(toUserUsername);
        return messageRepository.save(Message.builder()
                .body(messageDTO.getBody())
                .fromUser(from)
                .toUser(to)
                .build());
    }

    @Override
    public List<MessageResponseDTO> getMessageHistory(String participantUsername) {
        User to = userRepository
                .findByUsername(participantUsername)
                .orElseThrow(() -> new UserWithUsernameNotFound(participantUsername));
        User from = userRepository
                .findByUsername(getCurrentUserUsername())
                .orElseThrow();
        if (!from.getFriends().contains(to))
            throw new UserWithUsernameNotFriend(participantUsername);
        return messageRepository.findMessageHistory(participantUsername, getCurrentUserUsername()).stream()
                .map(message -> MessageResponseDTO.builder()
                        .id(message.getId())
                        .createdAt(message.getCreatedAt())
                        .body(message.getBody())
                        .fromUser(message.getFromUser().getUsername())
                        .toUser(message.getToUser().getUsername())
                        .build()
                )
                .toList();
    }

    @Override
    public List<String> getUsersWithMessageHistory() {
        return messageRepository.findUsersWithMessageHistory(getCurrentUserUsername());
    }

    private String getCurrentUserUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
