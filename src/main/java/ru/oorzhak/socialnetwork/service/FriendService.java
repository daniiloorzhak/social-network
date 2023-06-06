package ru.oorzhak.socialnetwork.service;

import java.util.List;

public interface FriendService {
    List<String> getFriendsList();
    List<String> getFriendRequestsUsernames();
    String sendFriendRequest(String username);
    String acceptFriendRequest(String username);
    String declineFriendRequest(String username);
    String deleteFriend(String username);
}
