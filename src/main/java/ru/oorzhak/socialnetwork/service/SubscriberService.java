package ru.oorzhak.socialnetwork.service;

import java.util.List;

public interface SubscriberService {
    List<String> getSubscribersList();
    void unfollowFromUser(String username);
}
