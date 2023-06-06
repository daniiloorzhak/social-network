package ru.oorzhak.socialnetwork.service;

import org.springframework.web.multipart.MultipartFile;
import ru.oorzhak.socialnetwork.dto.PostCreateAndUpdateDTO;
import ru.oorzhak.socialnetwork.dto.PostDetailsDTO;

import java.util.List;

public interface PostService {
    Long save(PostCreateAndUpdateDTO postCreateAndUpdateDTO, List<MultipartFile> images);
    List<PostDetailsDTO> getFeed(Integer page, Integer size);
    List<PostDetailsDTO> getUserPosts(String username);
    Long update(PostCreateAndUpdateDTO postDTO, List<MultipartFile> images, Long id);

    Long delete(Long id);
}
