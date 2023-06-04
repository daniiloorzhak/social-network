package ru.oorzhak.socialnetwork.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class MessageResponseDTO {
    private Long id;
    private Date createdAt;
    private String body;
    private String fromUser;
    private String toUser;
}
