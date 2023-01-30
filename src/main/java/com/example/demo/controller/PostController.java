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

    @GetMapping
    public ResponseEntity<List<PostEntity>> getPosts(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION).substring("Bearer ".length());
        return ResponseEntity.ok(postService.getPosts(jwtService.extractUsername(token)));
    }

    @PostMapping
    public ResponseEntity<PostEntity> createPost(HttpServletRequest request, @RequestBody PostDto postDto) {
        String token = request.getHeader(AUTHORIZATION).substring("Bearer ".length());
        return ResponseEntity.ok(postService.createPost(jwtService.extractUsername(token), postDto));
    }
}
