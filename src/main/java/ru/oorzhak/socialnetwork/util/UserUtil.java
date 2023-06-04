package ru.oorzhak.socialnetwork.util;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.oorzhak.socialnetwork.exception.UserWithUsernameNotFound;
import ru.oorzhak.socialnetwork.model.User;
import ru.oorzhak.socialnetwork.repository.UserRepository;

@Component
@AllArgsConstructor
public class UserUtil {
    private final UserRepository userRepository;

    private String getLoggedInUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public User getLoggedInUser() {
        return userRepository
                .findByUsername(getLoggedInUsername())
                .orElseThrow(() -> new UserWithUsernameNotFound(getLoggedInUsername()));
    }
}
