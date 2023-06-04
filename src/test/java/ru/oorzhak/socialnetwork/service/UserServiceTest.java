package ru.oorzhak.socialnetwork.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.oorzhak.socialnetwork.dto.UserRegisterDTO;
import ru.oorzhak.socialnetwork.exception.UserWithEmailAlreadyExists;
import ru.oorzhak.socialnetwork.exception.UserWithUsernameAlreadyExists;
import ru.oorzhak.socialnetwork.repository.UserRepository;
import ru.oorzhak.socialnetwork.service.impl.UserServiceImpl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void whenUsernameAlreadyExists_thenUserWithUsernameAlreadyExists() {
        when(userRepository.existsByUsername(any())).thenReturn(true);
        Assertions.assertThrows(UserWithUsernameAlreadyExists.class, () -> userService.save(new UserRegisterDTO()));
    }

    @Test
    public void whenEmailAlreadyExists_thenUserWithEmailAlreadyExists() {
        when(userRepository.existsByEmail(any())).thenReturn(true);
        Assertions.assertThrows(UserWithEmailAlreadyExists.class, () -> userService.save(new UserRegisterDTO()));
    }
}
