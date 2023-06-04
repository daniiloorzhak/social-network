package ru.oorzhak.socialnetwork.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserWithUsernameNotFriend extends RuntimeException {
    private final String username;

    @Override
    public String getMessage() {
        return "User with username " + username + " not a friend";
    }
}
