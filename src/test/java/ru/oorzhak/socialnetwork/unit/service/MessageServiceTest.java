package ru.oorzhak.socialnetwork.unit.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.oorzhak.socialnetwork.service.impl.MessageServiceImpl;
import ru.oorzhak.socialnetwork.unit.repository.MessageRepository;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {
    @Mock
    private MessageRepository messageRepository;
    @InjectMocks
    private MessageServiceImpl messageService;
}
