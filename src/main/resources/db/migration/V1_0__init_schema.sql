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

CREATE TABLE image
(
    id  SERIAL PRIMARY KEY,
    url VARCHAR(255) NOT NULL
);

CREATE TABLE messages
(
    id                serial PRIMARY KEY,
    created_at        TIMESTAMP    NOT NULL,
    body              VARCHAR(255) NOT NULL,
    sender_username   VARCHAR(255) NOT NULL,
    receiver_username VARCHAR(255) NOT NULL
);

CREATE TABLE user_role
(
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (role_id) REFERENCES roles (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE user_post
(
    user_id INT NOT NULL,
    post_id INT NOT NULL,
    PRIMARY KEY (user_id, post_id),
    FOREIGN KEY (post_id) REFERENCES roles (id),
    FOREIGN KEY (user_id) REFERENCES posts (id)
);

CREATE TABLE post_image
(
    post_id  INT NOT NULL,
    image_id INT NOT NULL,
    PRIMARY KEY (post_id, image_id),
    FOREIGN KEY (image_id) REFERENCES roles (id),
    FOREIGN KEY (post_id) REFERENCES posts (id)
);

CREATE TABLE subscribers
(
    user_id       INT NOT NULL,
    subscriber_id INT NOT NULL,
    PRIMARY KEY (user_id, subscriber_id),
    FOREIGN KEY (subscriber_id) REFERENCES users (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE friends
(
    user_id   INT NOT NULL,
    friend_id INT NOT NULL,
    PRIMARY KEY (user_id, friend_id),
    FOREIGN KEY (friend_id) REFERENCES roles (id),
    FOREIGN KEY (user_id) REFERENCES posts (id)
);

INSERT INTO roles (name)
VALUES ('ROLE_ADMIN'),
       ('ROLE_USER');
