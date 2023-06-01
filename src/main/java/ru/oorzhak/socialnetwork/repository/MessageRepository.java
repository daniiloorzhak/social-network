package ru.oorzhak.socialnetwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.oorzhak.socialnetwork.model.Message;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query(value = "SELECT m FROM Message m WHERE (m.senderUsername = ?1 and m.receiverUsername = ?2) or (m.senderUsername = ?2 and m.receiverUsername = ?1)")
    Optional<List<Message>> findMessageHistory(String username1, String username2);

//    Optional<List<Message>> findBySenderUsernameAndReceiverUsernameOrReceiverUsernameAndSenderUsername(String username1, String username2);
}
