package ru.oorzhak.socialnetwork.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.oorzhak.socialnetwork.dto.PostCreateAndUpdateDTO;
import ru.oorzhak.socialnetwork.dto.PostDetailsDTO;
import ru.oorzhak.socialnetwork.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/post")
@AllArgsConstructor
@Tag(name = "Post")
public class PostController {
    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostDetailsDTO>> getSubscriptionFeed(
            @RequestParam(name = "page", defaultValue = "0",  required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "10", required = false) Integer size) {
        return ResponseEntity.ok(postService.getFeed(page, size));
    }

    @GetMapping("{username}")
    public ResponseEntity<List<PostDetailsDTO>> getUserPosts(@PathVariable @NotBlank String username) {
        return ResponseEntity.ok(postService.getUserPosts(username));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Long> createPost(
            @RequestPart("post") @Valid PostCreateAndUpdateDTO postDTO,
            @RequestPart("images") List<MultipartFile> images) {
        return ResponseEntity.ok(postService.save(postDTO, images));
    }

    @PutMapping(value = "{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Long> updatePost(
            @RequestPart("post") @Valid PostCreateAndUpdateDTO postDTO,
            @RequestPart("images") List<MultipartFile> images,
            @PathVariable Long id) {
        return ResponseEntity.ok(postService.update(postDTO, images, id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Long> deletePost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.delete(id));
    }
}
