package ru.oorzhak.socialnetwork.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.oorzhak.socialnetwork.dto.MessageSendDTO;
import ru.oorzhak.socialnetwork.dto.MessageDetailsDTO;
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
    public Message sendMessage(String toUserUsername, MessageSendDTO messageSendDTO) {
        User to = getToUser(toUserUsername);
        User from = getFromUser();
        if (!from.getFriends().contains(to))
            throw new UserWithUsernameNotFriend(toUserUsername);
        return messageRepository.save(Message.builder()
                .body(messageSendDTO.getBody())
                .fromUser(from)
                .toUser(to)
                .build());
    }

    @Override
    public List<MessageDetailsDTO> getMessageHistory(String participantUsername) {
        User to = getToUser(participantUsername);
        User from = getFromUser();
        if (!from.getFriends().contains(to))
            throw new UserWithUsernameNotFriend(participantUsername);
        return messageRepository.findMessageHistory(participantUsername, getCurrentUserUsername()).stream()
                .map(MessageServiceImpl::messageToMessageDetailsDTO
                )
                .toList();
    }

    private static MessageDetailsDTO messageToMessageDetailsDTO(Message message) {
        return MessageDetailsDTO.builder()
                .id(message.getId())
                .createdAt(message.getCreatedAt())
                .body(message.getBody())
                .fromUserUsername(message.getFromUser().getUsername())
                .toUserUsername(message.getToUser().getUsername())
                .build();
    }

    @Override
    public List<String> getUsersWithMessageHistory() {
        return messageRepository
                .findUsersWithMessageHistory(
                        getCurrentUserUsername());
    }

    private String getCurrentUserUsername() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }

    private User getFromUser() {
        return userRepository
                .findByUsername(getCurrentUserUsername())
                .orElseThrow();
    }

    private User getToUser(String toUserUsername) {
        return userRepository
                .findByUsername(toUserUsername)
                .orElseThrow(() -> new UserWithUsernameNotFound(toUserUsername));
    }
}
