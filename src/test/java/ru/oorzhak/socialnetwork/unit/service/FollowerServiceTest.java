package ru.oorzhak.socialnetwork.unit.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.oorzhak.socialnetwork.repository.UserRepository;
import ru.oorzhak.socialnetwork.service.impl.FollowerServiceImpl;

@ExtendWith(MockitoExtension.class)
public class FollowerServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private FollowerServiceImpl followerService;
}
