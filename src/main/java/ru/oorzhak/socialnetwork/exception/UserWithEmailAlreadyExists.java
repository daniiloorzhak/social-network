package ru.oorzhak.socialnetwork.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserWithEmailAlreadyExists extends RuntimeException {
    private final String email;
    @Override
    public String getMessage() {
        return "User with email " + email + " already exists";
    }
}
