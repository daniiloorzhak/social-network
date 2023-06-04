package ru.oorzhak.socialnetwork.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserNotSendFriendRequest extends RuntimeException {
    private final String username;
    @Override
    public String getMessage() {
        return "User with username " + username + " did not send friend request";
    }
}
