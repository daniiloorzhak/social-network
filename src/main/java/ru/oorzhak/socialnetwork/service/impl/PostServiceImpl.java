package ru.oorzhak.socialnetwork.service.impl;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.oorzhak.socialnetwork.dto.PostCreateDTO;
import ru.oorzhak.socialnetwork.dto.PostDetailsDTO;
import ru.oorzhak.socialnetwork.exception.PostWithIdNotFoundException;
import ru.oorzhak.socialnetwork.exception.PostWithIdNotUserPost;
import ru.oorzhak.socialnetwork.exception.UserWithUsernameNotFound;
import ru.oorzhak.socialnetwork.model.Image;
import ru.oorzhak.socialnetwork.model.Post;
import ru.oorzhak.socialnetwork.model.User;
import ru.oorzhak.socialnetwork.repository.ImageRepository;
import ru.oorzhak.socialnetwork.repository.PostRepository;
import ru.oorzhak.socialnetwork.repository.UserRepository;
import ru.oorzhak.socialnetwork.service.PostService;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {
    @Value("${yandex.bucket.name}")
    private String bucketName;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final AmazonS3 amazonS3;
    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, ImageRepository imageRepository, AmazonS3 amazonS3) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
        this.amazonS3 = amazonS3;
    }

    @Override
    public List<PostDetailsDTO> getFeed(Integer page, Integer size) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        return postRepository
                .findAllSubscribedUserPosts(getFollowingUsernames(user), PageRequest.of(page, size))
                .stream()
                .map(this::postToPostDto)
                .toList();
    }

    @Override
    public List<PostDetailsDTO> getUserPosts(String username) {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UserWithUsernameNotFound(username));
        return user.getPosts().stream()
                .map(this::postToPostDto)
                .toList();
    }

    @Override
    @Transactional
    public Long save(PostCreateDTO postCreateDTO, List<MultipartFile> images) {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        User currentUser = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UserWithUsernameNotFound(username));
        List<String> urls = getImagesUrls(images);
        List<Image> imageList = urls.stream()
                .map(url -> Image.builder().url(url).build())
                .toList();
        Post post = Post.builder()
                .title(postCreateDTO.getTitle())
                .body(postCreateDTO.getBody())
                .images(imageList)
                .creator(currentUser)
                .build();
        return postRepository.save(post).getId();
    }

    @Override
    @Transactional
    public Long update(PostCreateDTO postDTO, List<MultipartFile> images, Long id) {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        User creator = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UserWithUsernameNotFound(username));
        Post post = postRepository
                .findById(id)
                .orElseThrow();
        if (!creator.getPosts().contains(post)) {
            throw new RuntimeException();
        }
        post.getImages().forEach(image -> amazonS3.deleteObject(bucketName, null));
        imageRepository.deleteAll(post.getImages());
        List<String> urls = getImagesUrls(images);
        post = Post.builder()
                .id(post.getId())
                .title(postDTO.getTitle() != null ? postDTO.getTitle() : post.getTitle())
                .body(postDTO.getBody() != null ? postDTO.getBody() : post.getBody())
                .images(urls.stream()
                        .map(url -> Image.builder().url(url).build())
                        .toList())
                .build();

        postRepository.save(post);
        return post.getId();
    }

    @Override
    @Transactional
    public Long delete(Long id) {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        User currentUser = userRepository
                .findByUsername(username)
                .orElseThrow();
        Post post = postRepository
                .findById(id)
                .orElseThrow(() -> new PostWithIdNotFoundException(id));
        if (currentUser.getPosts().contains(post))
            throw new PostWithIdNotUserPost(id);
        for (var image : post.getImages()) {
            try {
                amazonS3.deleteObject(bucketName, image.getId());
            } catch (SdkClientException e) {
                throw new RuntimeException(e);
            }
        }
        postRepository.delete(post);
        return post.getId();
    }

    private List<String> getImagesUrls(List<MultipartFile> images) {
        List<String> urls = new LinkedList<>();
        for (var image : images) {
            try (var inputStream = image.getInputStream()){
                String uuid = UUID.randomUUID().toString();
                var metadata = new ObjectMetadata();
                metadata.setContentType(image.getContentType());
                metadata.setContentLength(image.getSize());
                amazonS3.putObject(bucketName, uuid, inputStream, metadata);
                urls.add(amazonS3.getUrl(bucketName, uuid).toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return urls;
    }


    public PostDetailsDTO postToPostDto(Post post) {
        return PostDetailsDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .body(post.getBody())
                .createdAt(post.getCreatedAt())
                .creator(post.getCreator().getUsername())
                .imageUrls(post.getImages().stream().map(Image::getUrl).toList())
                .build();
    }

    private static List<String> getFollowingUsernames(User user) {
        return user.getFollowing().stream()
                .map(User::getUsername)
                .toList();
    }
}
