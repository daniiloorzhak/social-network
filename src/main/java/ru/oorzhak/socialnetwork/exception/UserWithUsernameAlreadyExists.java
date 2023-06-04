package ru.oorzhak.socialnetwork.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserWithUsernameAlreadyExists extends RuntimeException {
    private final String username;
    @Override
    public String getMessage() {
        return "User with username " + username + " already exists";
    }
}
