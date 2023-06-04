package ru.oorzhak.socialnetwork.service;

import org.springframework.transaction.annotation.Transactional;
import ru.oorzhak.socialnetwork.dto.MessageSendDTO;
import ru.oorzhak.socialnetwork.dto.MessageDetailsDTO;
import ru.oorzhak.socialnetwork.model.Message;

import java.util.List;

public interface MessageService {
    Message sendMessage(String username, MessageSendDTO messageSendDTO);
    @Transactional
    List<MessageDetailsDTO> getMessageHistory(String receiverName);
    List<String> getUsersWithMessageHistory();
}
