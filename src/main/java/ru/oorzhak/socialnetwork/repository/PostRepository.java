package ru.oorzhak.socialnetwork.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.oorzhak.socialnetwork.model.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(value = """
                SELECT post
                FROM Post post
                WHERE post.creator.username IN :subscribersUsernames
                ORDER BY post.createdAt DESC
    """)
    List<Post> findAllSubscribedUserPosts(List<String> subscribersUsernames, Pageable pageable);
}
