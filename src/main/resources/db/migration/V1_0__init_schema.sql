CREATE TABLE roles
(
    id   serial PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE users
(
    id       serial PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    email    VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(512)        NOT NULL
);

CREATE TABLE posts
(
    id         serial PRIMARY KEY,
    title      VARCHAR(255) NOT NULL,
    body       VARCHAR(255) NOT NULL,
    created_at TIMESTAMP    NOT NULL
);

CREATE TABLE images
(
    id   SERIAL PRIMARY KEY,
    uuid VARCHAR(255) UNIQUE NOT NULL,
    url  VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE messages
(
    id                serial PRIMARY KEY,
    created_at        TIMESTAMP    NOT NULL,
    body              VARCHAR(255) NOT NULL
);

CREATE TABLE message_from_user
(
    message_id   INT NOT NULL,
    from_user_id INT NOT NULL,
    PRIMARY KEY (message_id, from_user_id),
    FOREIGN KEY (message_id) REFERENCES messages (id),
    FOREIGN KEY (from_user_id) REFERENCES users (id)
);

CREATE TABLE message_to_user
(
    message_id   INT NOT NULL,
    to_user_id   INT NOT NULL,
    PRIMARY KEY (message_id, to_user_id),
    FOREIGN KEY (message_id) REFERENCES messages (id),
    FOREIGN KEY (to_user_id) REFERENCES users (id)
);

CREATE TABLE user_role
(
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE user_post
(
    user_id INT NOT NULL,
    post_id INT NOT NULL,
    PRIMARY KEY (user_id, post_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (post_id) REFERENCES posts (id)
);

CREATE TABLE post_image
(
    post_id  INT NOT NULL,
    image_id INT NOT NULL,
    PRIMARY KEY (post_id, image_id),
    FOREIGN KEY (post_id) REFERENCES posts (id),
    FOREIGN KEY (image_id) REFERENCES images (id)
);

CREATE TABLE followers
(
    user_id     INT NOT NULL,
    follower_id INT NOT NULL,
    PRIMARY KEY (user_id, follower_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (follower_id) REFERENCES users (id)
);

CREATE TABLE friends
(
    user_id   INT NOT NULL,
    friend_id INT NOT NULL,
    PRIMARY KEY (user_id, friend_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (friend_id) REFERENCES users (id)
);

CREATE TABLE friend_request
(
    user_id   INT NOT NULL,
    sender_id INT NOT NULL,
    PRIMARY KEY (user_id, sender_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (sender_id) REFERENCES users (id)
);

INSERT INTO roles (name)
VALUES ('ROLE_ADMIN'),
       ('ROLE_USER');
