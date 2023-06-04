package ru.oorzhak.socialnetwork.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRegisterDTO {
    @NotBlank
    private String username;
    @Email
    @NotNull
    private String email;
    @NotBlank
    private String password;
}
