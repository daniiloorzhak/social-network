package ru.oorzhak.socialnetwork.service;

import org.springframework.web.multipart.MultipartFile;
import ru.oorzhak.socialnetwork.dto.PostCreateDTO;
import ru.oorzhak.socialnetwork.dto.PostDetailsDTO;

import java.util.List;

public interface PostService {
    Long save(PostCreateDTO postCreateDTO, List<MultipartFile> images);
    List<PostDetailsDTO> getFeed(Integer page, Integer size);
    List<PostDetailsDTO> getUserPosts(String username);
    void update(PostCreateDTO postDTO, List<MultipartFile> images, Long id);
}
