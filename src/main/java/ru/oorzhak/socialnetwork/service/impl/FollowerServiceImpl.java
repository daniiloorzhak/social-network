package ru.oorzhak.socialnetwork.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.oorzhak.socialnetwork.model.User;
import ru.oorzhak.socialnetwork.service.FollowerService;
import ru.oorzhak.socialnetwork.service.UserService;

import java.util.List;

@Service
@AllArgsConstructor
public class FollowerServiceImpl implements FollowerService {
    private final UserService userService;
    @Override
    public List<String> getFollowersUsernamesList() {
        return userService.getLoggedInUser().getFollowers().stream()
                .map(User::getUsername)
                .toList();
    }
}
