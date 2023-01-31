package com.example.demo.controller;

import com.example.demo.config.JwtService;
import com.example.demo.dto.PostDto;
import com.example.demo.entity.PostEntity;
import com.example.demo.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/api/v1/posts")
@AllArgsConstructor
public class PostController {

    private PostService postService;
    private JwtService jwtService;

    @GetMapping("/all")
    public ResponseEntity<List<PostEntity>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping
    public ResponseEntity<List<PostEntity>> getAuthenticatedUsersPosts(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION).substring("Bearer ".length());
        return ResponseEntity.ok(postService.getAuthenticatedUsersPosts(jwtService.extractUsername(token)));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<PostEntity>> getUsersPosts(@PathVariable Long userId) {
        return ResponseEntity.ok(postService.getUsersPosts(userId));
    }

    @GetMapping("/post")
    public ResponseEntity<PostEntity> getPost(@RequestParam Long postId) {
        return ResponseEntity.ok(postService.getPost(postId));
    }

    @PostMapping
    public ResponseEntity<PostEntity> createPost(HttpServletRequest request, @RequestBody PostDto postDto) {
        String token = request.getHeader(AUTHORIZATION).substring("Bearer ".length());
        return ResponseEntity.ok(postService.createPost(jwtService.extractUsername(token), postDto));
    }

    @PutMapping
    public ResponseEntity<PostEntity> updatePost(HttpServletRequest request, @RequestParam Long postId, @RequestBody PostDto postDto) {
        String token = request.getHeader(AUTHORIZATION).substring("Bearer ".length());
        return ResponseEntity.ok(postService.updatePost(jwtService.extractUsername(token), postId, postDto));
    }

    @DeleteMapping
    public ResponseEntity<String> deletePost(HttpServletRequest request, @RequestParam Long postId) {
        String token = request.getHeader(AUTHORIZATION).substring("Bearer ".length());
        return ResponseEntity.ok(postService.deletePost(jwtService.extractUsername(token), postId));
    }
}
