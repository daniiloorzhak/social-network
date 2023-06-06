package ru.oorzhak.socialnetwork.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PostWithIdNotFoundException extends RuntimeException {
    private final Long id;

    @Override
    public String getMessage() {
        return "Post with id " + id + " not found";
    }
}
