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
    private final String NAME = SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getName();
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Override
    public List<String> getUsersWithMessageHistory() {
        return messageRepository.findUsersWithMessageHistory(NAME);
    }

    @Override
    public List<MessageDetailsDTO> getMessageHistory(String participantUsername) {
        User from = userRepository
                .findByUsername(NAME)
                .orElseThrow();
        User to = userRepository
                .findByUsername(participantUsername)
                .orElseThrow(() -> new UserWithUsernameNotFound(participantUsername));
        if (!from.getFriends().contains(to) && !to.getFriends().contains(from))
            throw new UserWithUsernameNotFriend(to.getUsername());
        return messageRepository
                .findMessageHistory(participantUsername, NAME).stream()
                .map(MessageServiceImpl::messageToMessageDetailsDTO)
                .toList();
    }

    @Override
    @Transactional
    public Message sendMessage(String participantUsername, MessageSendDTO messageSendDTO) {
        User from = userRepository
                .findByUsername(NAME)
                .orElseThrow();
        User to = userRepository
                .findByUsername(participantUsername)
                .orElseThrow(() -> new UserWithUsernameNotFound(participantUsername));
        if (!from.getFriends().contains(to) && !to.getFriends().contains(from))
            throw new UserWithUsernameNotFriend(to.getUsername());
        Message message = Message.builder()
                .body(messageSendDTO.getBody())
                .fromUser(from)
                .toUser(to)
                .build();
        return messageRepository.save(message);
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
}
