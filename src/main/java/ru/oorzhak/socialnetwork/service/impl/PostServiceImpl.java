package ru.oorzhak.socialnetwork.service.impl;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.oorzhak.socialnetwork.dto.PostCreateAndUpdateDTO;
import ru.oorzhak.socialnetwork.dto.PostDetailsDTO;
import ru.oorzhak.socialnetwork.exception.PostWithIdNotFoundException;
import ru.oorzhak.socialnetwork.exception.PostWithIdNotUserPost;
import ru.oorzhak.socialnetwork.model.Image;
import ru.oorzhak.socialnetwork.model.Post;
import ru.oorzhak.socialnetwork.model.User;
import ru.oorzhak.socialnetwork.repository.ImageRepository;
import ru.oorzhak.socialnetwork.repository.PostRepository;
import ru.oorzhak.socialnetwork.service.PostService;
import ru.oorzhak.socialnetwork.service.UserService;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {
    @Value("${yandex.bucket.name}")
    private String bucketName;
    private final PostRepository postRepository;
    private final ImageRepository imageRepository;
    private final UserService userService;
    private final AmazonS3 amazonS3;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, ImageRepository imageRepository, UserService userService, AmazonS3 amazonS3) {
        this.postRepository = postRepository;
        this.imageRepository = imageRepository;
        this.userService = userService;
        this.amazonS3 = amazonS3;
    }

    @Override
    public List<PostDetailsDTO> getFeed(Integer page, Integer size) {
        User user = userService.getLoggedInUser();
        return postRepository
                .findAllSubscribedUserPosts(getFollowingUsernames(user), PageRequest.of(page, size))
                .stream()
                .map(this::postToPostDto)
                .toList();
    }

    @Override
    public List<PostDetailsDTO> getUserPosts(String username) {
        User user = userService.getUserByUsername(username);
        return user.getPosts().stream()
                .map(this::postToPostDto)
                .toList();
    }

    @Override
    @Transactional
    public Long save(PostCreateAndUpdateDTO postCreateAndUpdateDTO, List<MultipartFile> images) {
        User creator = userService.getLoggedInUser();
        List<Image> imageList = uploadImagesAndGetImageEntityList(images);
        Post post = Post.builder()
                .title(postCreateAndUpdateDTO.getTitle())
                .body(postCreateAndUpdateDTO.getBody())
                .images(imageList)
                .creator(creator)
                .build();
        return postRepository.save(post).getId();
    }

    @Override
    @Transactional
    public Long update(PostCreateAndUpdateDTO postDTO, List<MultipartFile> images, Long id) {
        User creator = userService.getLoggedInUser();
        Post post = getPostById(id);
        if (!creator.getPosts().contains(post)) throw new PostWithIdNotUserPost(id);
        for (var image1 : post.getImages()) {
            try {
                amazonS3.deleteObject(bucketName, image1.getUuid());
            } catch (SdkClientException e) {
                throw new RuntimeException(e);
            }
        }
        imageRepository.deleteAll(post.getImages());
        List<Image> imageList = uploadImagesAndGetImageEntityList(images);
        post = Post.builder()
                .id(post.getId())
                .title(postDTO.getTitle() != null ? postDTO.getTitle() : post.getTitle())
                .body(postDTO.getBody() != null ? postDTO.getBody() : post.getBody())
                .images(imageList)
                .creator(creator)
                .build();
        return postRepository.save(post).getId();
    }

    @Override
    @Transactional
    public Long delete(Long id) {
        User creator = userService.getLoggedInUser();
        Post post = getPostById(id);
        if (!creator.getPosts().contains(post)) throw new PostWithIdNotUserPost(id);
        for (var image : post.getImages()) {
            try {
                amazonS3.deleteObject(bucketName, image.getUuid());
            } catch (SdkClientException e) {
                throw new RuntimeException(e);
            }
        }
        postRepository.delete(post);
        return id;
    }

    private List<Image> uploadImagesAndGetImageEntityList(List<MultipartFile> images) {
        List<Image> imageList = new LinkedList<>();
        for (var image : images) {
            try (var inputStream = image.getInputStream()){
                String uuid = UUID.randomUUID().toString();
                var metadata = new ObjectMetadata();
                metadata.setContentType(image.getContentType());
                metadata.setContentLength(image.getSize());
                amazonS3.putObject(bucketName, uuid, inputStream, metadata);
                String url = amazonS3.getUrl(bucketName, uuid).toString();
                imageList.add(Image.builder()
                        .uuid(uuid)
                        .url(url)
                        .build());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return imageList;
    }

    private Post getPostById(Long id) {
        return postRepository
                .findById(id)
                .orElseThrow(() -> new PostWithIdNotFoundException(id));
    }

    private PostDetailsDTO postToPostDto(Post post) {
        return PostDetailsDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .body(post.getBody())
                .createdAt(post.getCreatedAt())
                .creator(post.getCreator().getUsername())
                .imageUrls(post.getImages().stream().map(Image::getUrl).toList())
                .build();
    }

    private List<String> getFollowingUsernames(User user) {
        return user.getFollowing().stream()
                .map(User::getUsername)
                .toList();
    }
}
