package ru.oorzhak.socialnetwork.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.oorzhak.socialnetwork.exception.*;
import ru.oorzhak.socialnetwork.model.User;
import ru.oorzhak.socialnetwork.repository.UserRepository;
import ru.oorzhak.socialnetwork.service.FriendService;

import java.util.List;

@Service
@AllArgsConstructor
public class FriendServiceImpl implements FriendService {
    private final UserRepository userRepository;

    @Override
    public List<String> getFriendRequestsUsernames() {
        return getCurrentUser().getFriendRequest().stream()
                .map(User::getUsername)
                .toList();
    }

    @Override
    public List<String> getFriendsList() {
        return getCurrentUser().getFriends().stream()
                .map(User::getUsername)
                .toList();
    }

    @Override
    @Transactional
    public String sendFriendRequest(String username) {
        User currentUser = getCurrentUser();
        User friend = getFriend(username);
        if (currentUser.getFriends().contains(friend))
            throw new UserWithUsernameAlreadyFriend(username);
        currentUser.getFriendRequest().add(friend);
        currentUser.getFollowers().add(friend);
        return userRepository.save(currentUser).getUsername();
    }

    @Override
    @Transactional
    public String acceptFriendRequest(String username) {
        User currentUser = getCurrentUser();
        User friend = getFriend(username);
        if (!currentUser.getFriendRequest().contains(friend)) {
            throw new UserNotSendFriendRequest(username);
        }
        currentUser.getFriendRequest().remove(friend);
        currentUser.getFriends().add(friend);
        friend.getFriends().add(currentUser);
        userRepository.save(currentUser);
        userRepository.save(friend);
        return friend.getUsername();
    }

    @Override
    @Transactional
    public String declineFriendRequest(String username) {
        User currentUser = getCurrentUser();
        User friend = getFriend(username);
        if (!currentUser.getFriendRequest().contains(friend)) {
            throw new UserNotSendFriendRequest(username);
        }
        currentUser.getFriendRequest().remove(friend);
        userRepository.save(currentUser);
        return friend.getUsername();
    }

    @Override
    @Transactional
    public String deleteFriend(String username) {
        User currentUser = getCurrentUser();
        User friend = getFriend(username);
        if (!currentUser.getFriends().contains(friend))
            throw new UserWithUsernameNotFriend(username);
        currentUser.getFriends().remove(friend);
        friend.getFriends().remove(currentUser);
        currentUser.getFollowers().remove(friend);
        userRepository.save(currentUser);
        userRepository.save(friend);
        return friend.getUsername();
    }

    private User getFriend(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UserWithUsernameNotFound(username));
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UserWithUsernameNotFound(username));
    }
}
