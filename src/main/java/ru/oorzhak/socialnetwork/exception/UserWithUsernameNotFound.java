package ru.oorzhak.socialnetwork.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserWithUsernameNotFound extends RuntimeException {
    private final String username;
    @Override
    public String getMessage() {
        return "User with username " + username + "not found";
    }
}
