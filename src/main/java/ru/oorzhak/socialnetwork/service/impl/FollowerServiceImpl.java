package ru.oorzhak.socialnetwork.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.oorzhak.socialnetwork.model.User;
import ru.oorzhak.socialnetwork.repository.UserRepository;
import ru.oorzhak.socialnetwork.service.FollowerService;

import java.util.List;

@Service
@AllArgsConstructor
public class FollowerServiceImpl implements FollowerService {
    private final UserRepository userRepository;
    @Override
    public List<String> getSubscribersList() {
        User currentUser = userRepository
                .findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow();
        return currentUser.getFollowers().stream()
                .map(User::getUsername)
                .toList();
    }

    @Override
    @Transactional
    public void unfollowFromUser(String username) {
        User currentUser = userRepository
                .findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow();
        User followedOn = userRepository
                .findByUsername(username)
                .orElseThrow();
        if (followedOn.getFriendRequest().contains(currentUser))
            followedOn.getFriends().remove(currentUser);
        currentUser.getFollowing().remove(followedOn);
        currentUser.getFollowers().remove(currentUser);
        userRepository.save(currentUser);
        userRepository.save(followedOn);
    }
}
