package ru.oorzhak.socialnetwork.exception;

public class UserNotSendFriendRequest extends RuntimeException {
    private final String username;
    public UserNotSendFriendRequest(String username) {
        this.username = username;
    }

    @Override
    public String getMessage() {
        return "User with username " + username + " did not send friend request";
    }
}
