package ru.oorzhak.socialnetwork.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.oorzhak.socialnetwork.repository.PostRepository;
import ru.oorzhak.socialnetwork.service.impl.PostServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {
    @Mock
    private PostRepository postRepository;
    @InjectMocks
    private PostServiceImpl postService;

    @Test
    public void assertTrue() {
        Assertions.assertTrue(true);
    }
}
