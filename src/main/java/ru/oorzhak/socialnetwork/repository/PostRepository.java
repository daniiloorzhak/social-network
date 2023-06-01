package ru.oorzhak.socialnetwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.oorzhak.socialnetwork.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
