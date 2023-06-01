package ru.oorzhak.socialnetwork.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class PostDTO {
    private Long id;
    private String creator;
    private String title;
    private String body;
    private Date createdAt;
    private List<String> imageUrls;
}
