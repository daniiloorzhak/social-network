package ru.oorzhak.socialnetwork.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostCreateAndUpdateDTO {
    @NotBlank
    private String title;
    @NotBlank
    private String body;
}
