package ru.oorzhak.socialnetwork.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MessageSendDTO {
    @NotBlank
    private String body;
}
