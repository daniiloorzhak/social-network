package ru.oorzhak.socialnetwork.service;

import org.springframework.web.multipart.MultipartFile;
import ru.oorzhak.socialnetwork.dto.PostCreateDTO;
import ru.oorzhak.socialnetwork.dto.PostDTO;

import java.util.List;

public interface PostService {
    Long save(PostCreateDTO postCreateDTO, List<MultipartFile> images);
    List<PostDTO> getFeed(Long page, Long size);
    List<PostDTO> getUserPosts(String username);
}
