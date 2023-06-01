package ru.oorzhak.socialnetwork.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostCreateDTO {
    @NotBlank
    private String title;
    @NotBlank
    private String body;
}
