package ru.oorzhak.socialnetwork.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.oorzhak.socialnetwork.dto.UserRegisterDTO;
import ru.oorzhak.socialnetwork.exception.UserWithEmailAlreadyExists;
import ru.oorzhak.socialnetwork.exception.UserWithUsernameAlreadyExists;
import ru.oorzhak.socialnetwork.exception.UserWithUsernameNotFound;
import ru.oorzhak.socialnetwork.model.Role;
import ru.oorzhak.socialnetwork.model.User;
import ru.oorzhak.socialnetwork.repository.UserRepository;
import ru.oorzhak.socialnetwork.service.UserService;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User signup(UserRegisterDTO userRegisterDTO) {
        if (userRepository.existsByEmail(userRegisterDTO.getEmail()))
            throw new UserWithEmailAlreadyExists(userRegisterDTO.getEmail());
         if (userRepository.existsByUsername(userRegisterDTO.getUsername()))
             throw new UserWithUsernameAlreadyExists(userRegisterDTO.getUsername());

        return userRepository.save(userRegisterDtoToUser(userRegisterDTO));
    }

    @Override
    public String getLoggedInUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public User getLoggedInUser() {
        return getUserByUsername(getLoggedInUsername());
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UserWithUsernameNotFound(username));
    }

    private User userRegisterDtoToUser(UserRegisterDTO userRegisterDTO) {
        return User.builder()
                .username(userRegisterDTO.getUsername())
                .email(userRegisterDTO.getEmail())
                .password(passwordEncoder.encode(userRegisterDTO.getPassword()))
                .roles(List.of(Role.USER_ROLE))
                .build();
    }
}
