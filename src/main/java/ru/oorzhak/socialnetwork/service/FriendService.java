package ru.oorzhak.socialnetwork.service;

import java.util.List;

public interface FriendService {
    void sendFriendRequest(String username);
    List<String> getFriendsList();
    void acceptFriendRequest(String username);
    void declineFriendRequest(String username);
    void deleteFriend(String username);
}
