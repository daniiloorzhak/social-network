package ru.oorzhak.socialnetwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.oorzhak.socialnetwork.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);
}
