package ru.oorzhak.socialnetwork.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.oorzhak.socialnetwork.exception.UserNotSendFriendRequest;
import ru.oorzhak.socialnetwork.exception.UserWithUsernameNotFound;
import ru.oorzhak.socialnetwork.model.User;
import ru.oorzhak.socialnetwork.repository.UserRepository;
import ru.oorzhak.socialnetwork.service.FriendService;

import java.util.List;

@Service
@AllArgsConstructor
public class FriendServiceImpl implements FriendService {
    private final UserRepository userRepository;
    @Override
    public List<String> getFriendsList() {
        User currentUser = userRepository
                .findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow();
        return currentUser.getFriends().stream()
                .map(User::getUsername)
                .toList();
    }

    @Override
    @Transactional
    public void sendFriendRequest(String username) {
        User currentUser = userRepository
                .findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow();
        User friend = userRepository
                .findByUsername(username)
                .orElseThrow();
        currentUser.getFriendRequest().add(friend);
        currentUser.getFollowers().add(friend);
        userRepository.save(currentUser);
    }

    @Override
    @Transactional
    public void acceptFriendRequest(String username) {
        User currentUser = userRepository
                .findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow();
        User friend = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UserWithUsernameNotFound(username));
        if (!currentUser.getFriendRequest().contains(friend)) {
            throw new UserNotSendFriendRequest(username);
        }
        currentUser.getFriendRequest().remove(friend);
        currentUser.getFriends().add(friend);
        friend.getFriends().add(currentUser);
        userRepository.save(currentUser);
    }

    @Override
    @Transactional
    public void declineFriendRequest(String username) {
        User currentUser = userRepository
                .findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow();
        User friend = userRepository
                .findByUsername(username)
                .orElseThrow();
        if (!currentUser.getFriendRequest().contains(friend)) {
            throw new UserNotSendFriendRequest(username);
        }
        currentUser.getFriendRequest().remove(friend);
        userRepository.save(currentUser);
    }

    @Override
    @Transactional
    public void deleteFriend(String username) {
        User currentUser = userRepository
                .findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow();
        User friend = userRepository
                .findByUsername(username)
                .orElseThrow();
        currentUser.getFriends().remove(friend);
        friend.getFriends().remove(currentUser);
        userRepository.save(currentUser);
    }
}
