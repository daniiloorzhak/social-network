package ru.oorzhak.socialnetwork.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.oorzhak.socialnetwork.dto.UserRegisterDTO;
import ru.oorzhak.socialnetwork.exception.UserWithEmailAlreadyExists;
import ru.oorzhak.socialnetwork.exception.UserWithUsernameAlreadyExists;
import ru.oorzhak.socialnetwork.model.User;
import ru.oorzhak.socialnetwork.repository.UserRepository;
import ru.oorzhak.socialnetwork.service.UserService;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    @Transactional
    public User save(UserRegisterDTO userRegisterDTO) {
        if (userRepository.existsByEmail(userRegisterDTO.getEmail()))
            throw new UserWithEmailAlreadyExists(userRegisterDTO.getEmail());
         if (userRepository.existsByUsername(userRegisterDTO.getUsername()))
             throw new UserWithUsernameAlreadyExists(userRegisterDTO.getUsername());

        return userRepository.save(userRegisterDtoToUser(userRegisterDTO));
    }

    public User userRegisterDtoToUser(UserRegisterDTO userRegisterDTO) {
        return User.builder()
                .username(userRegisterDTO.getUsername())
                .email(userRegisterDTO.getEmail())
                .password(passwordEncoder.encode(userRegisterDTO.getPassword()))
                .build();
    }
}