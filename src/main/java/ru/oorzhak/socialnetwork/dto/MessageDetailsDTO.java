package ru.oorzhak.socialnetwork.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class MessageDetailsDTO {
    private Long id;
    private Date createdAt;
    private String body;
    private String fromUserUsername;
    private String toUserUsername;
}
