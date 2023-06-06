package ru.oorzhak.socialnetwork.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PostWithIdNotUserPost extends RuntimeException {
    private final Long id;

    @Override
    public String getMessage() {
        return "Post with " + id + " is not current user's";
    }
}
