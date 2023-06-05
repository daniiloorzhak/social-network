package ru.oorzhak.socialnetwork.service;

import java.util.List;

public interface FollowerService {
    List<String> getSubscribersList();
    void unfollowFromUser(String username);
}
