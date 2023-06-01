package ru.oorzhak.socialnetwork.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.oorzhak.socialnetwork.dto.PostCreateDTO;
import ru.oorzhak.socialnetwork.dto.PostDTO;
import ru.oorzhak.socialnetwork.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/post")
@AllArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping
    public ResponseEntity<?> getSubscriptionFeed(
            @RequestParam(name = "page", defaultValue = "1") Long page,
            @RequestParam(name = "size", defaultValue = "10") Long size) {
        return ResponseEntity.ok(postService.getFeed(page, size));
    }

    @GetMapping("{username}")
    public ResponseEntity<List<PostDTO>> getUserPosts(@PathVariable @NotBlank String username) {
        return ResponseEntity.ok(postService.getUserPosts(username));
    }

    @PostMapping
    public ResponseEntity<Long> createPost(@RequestBody @Valid PostCreateDTO postCreateDTO, List<MultipartFile> images) {
        return ResponseEntity.ok(postService.save(postCreateDTO, images));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updatePost(@RequestBody @Valid Object postDTO, List<MultipartFile> images, @PathVariable Long id) {
        return null;
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        return null;
    }
}
