package ru.oorzhak.socialnetwork.service;

import ru.oorzhak.socialnetwork.dto.UserRegisterDTO;
import ru.oorzhak.socialnetwork.model.User;

public interface UserService {
    User signup(UserRegisterDTO userRegisterDTO);
}
