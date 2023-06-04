package ru.oorzhak.socialnetwork.service;

import org.springframework.transaction.annotation.Transactional;
import ru.oorzhak.socialnetwork.dto.MessageDTO;
import ru.oorzhak.socialnetwork.dto.MessageResponseDTO;
import ru.oorzhak.socialnetwork.model.Message;

import java.util.List;

public interface MessageService {
    Message sendMessage(String username, MessageDTO messageDTO);
    @Transactional
    List<MessageResponseDTO> getMessageHistory(String receiverName);
    List<String> getUsersWithMessageHistory();
}
