package ru.oorzhak.socialnetwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.oorzhak.socialnetwork.model.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query(value = """
                SELECT m
                FROM Message m
                WHERE ((m.fromUser.username = ?1 and m.toUser.username = ?2) or
                (m.fromUser.username = ?2 and m.toUser.username = ?1))
                ORDER BY m.createdAt DESC
    """)
    List<Message> findMessageHistory(String username1, String username2);

    @Query(value = """
            SELECT DISTINCT
            CASE WHEN m.toUser.username = ?1 THEN m.fromUser.username END,
            CASE WHEN m.fromUser.username = ?1 THEN m.toUser.username END
            FROM Message m
            WHERE m.fromUser.username = ?1 OR m.toUser.username = ?1
    """)
    List<String> findUsersWithMessageHistory(String username);
}
