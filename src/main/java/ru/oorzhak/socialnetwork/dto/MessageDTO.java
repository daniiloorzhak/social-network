package ru.oorzhak.socialnetwork.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MessageDTO {
    @NotBlank
    private String body;
}
