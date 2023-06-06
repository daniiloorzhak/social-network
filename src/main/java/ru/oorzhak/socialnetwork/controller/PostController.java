package ru.oorzhak.socialnetwork.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.oorzhak.socialnetwork.dto.PostCreateDTO;
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
            @RequestParam(name = "page", defaultValue = "0", required = false) @Size(min = 0) Integer page,
            @RequestParam(name = "size", defaultValue = "10", required = false) @Size(min = 1) Integer size) {
        return ResponseEntity.ok(postService.getFeed(page, size));
    }

    @GetMapping("{username}")
    public ResponseEntity<List<PostDetailsDTO>> getUserPosts(@PathVariable @NotBlank String username) {
        return ResponseEntity.ok(postService.getUserPosts(username));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Long> createPost(@RequestPart("post") @Valid PostCreateDTO postCreateDTO, @RequestPart("images") List<MultipartFile> images) {
        return ResponseEntity.ok(postService.save(postCreateDTO, images));
    }

    @PutMapping("{id}")
    public ResponseEntity<Long> updatePost(@RequestBody @Valid PostCreateDTO postDTO, List<MultipartFile> images, @PathVariable Long id) {
        return ResponseEntity.ok(postService.update(postDTO, images, id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Long> deletePost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.delete(id));
    }
}
