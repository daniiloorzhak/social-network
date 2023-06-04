package ru.oorzhak.socialnetwork.exception;

public class UserWithUsernameNotFriend extends RuntimeException {
    private final String username;

    public UserWithUsernameNotFriend(String username) {
        this.username = username;
    }

    @Override
    public String getMessage() {
        return "User with username " + username + " not a friend";
    }
}
