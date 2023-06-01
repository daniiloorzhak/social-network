package ru.oorzhak.socialnetwork.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.oorzhak.socialnetwork.dto.PostCreateDTO;
import ru.oorzhak.socialnetwork.dto.PostDTO;
import ru.oorzhak.socialnetwork.exception.UserWithUsernameNotFound;
import ru.oorzhak.socialnetwork.model.Image;
import ru.oorzhak.socialnetwork.model.Post;
import ru.oorzhak.socialnetwork.model.User;
import ru.oorzhak.socialnetwork.repository.PostRepository;
import ru.oorzhak.socialnetwork.repository.UserRepository;
import ru.oorzhak.socialnetwork.service.PostService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Value("{yandex.bucket.name}")
    private String bucketName;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AmazonS3 amazonS3;
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, AmazonS3 amazonS3) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.amazonS3 = amazonS3;
    }

    @Override
    @Transactional
    public Long save(PostCreateDTO postCreateDTO, List<MultipartFile> images) {
        Post post = postCreateDtoToPost(postCreateDTO);
        List<String> urls = new ArrayList<>();
        for (var image : images) {
            try (var inputStream = image.getInputStream()){
                String uuid = UUID.randomUUID().toString();
                amazonS3.putObject(bucketName, uuid, inputStream, null);
                urls.add(amazonS3.getUrl(bucketName, uuid).toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        post.setImages(urls.stream()
                .map(url -> Image.builder().url(url).build())
                .toList());
        postRepository.save(post);
        return post.getId();
    }

    @Override
    public List<PostDTO> getFeed(Long page, Long size) {
        if (page < 1)
            throw new RuntimeException();
        if (size < 10)
            throw new RuntimeException();
        return postRepository.findAll(PageRequest.of(page.intValue(), size.intValue())).get()
                .map(this::postToPostDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDTO> getUserPosts(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserWithUsernameNotFound(username));
        return user.get
    }

    public Post postCreateDtoToPost(PostCreateDTO postCreateDTO) {
        return Post.builder()
                .title(postCreateDTO.getTitle())
                .body(postCreateDTO.getBody())
                .build();
    }

    public PostDTO postToPostDto(Post post) {
        return PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .body(post.getBody())
                .imageUrls(post.getImages().stream().map(Image::getUrl).toList())
                .build();
    }

//    public List<PostDTO> postListToPostDTOList()
}
