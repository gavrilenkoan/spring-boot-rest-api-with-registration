package com.example.demo.controller;

import com.example.demo.config.JwtService;
import com.example.demo.dto.ReactionDto;
import com.example.demo.entity.ReactionEntity;
import com.example.demo.service.ReactionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/api/v1/reactions")
@AllArgsConstructor
public class ReactionController {

    private ReactionService reactionService;
    private JwtService jwtService;

    @GetMapping
    public ResponseEntity<List<ReactionEntity>> getAuthenticatedUserReactions(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION).substring("Bearer ".length());
        return ResponseEntity.ok(reactionService.getAuthenticatedUserReactions(jwtService.extractUsername(token)));
    }

    @PostMapping
    public ResponseEntity<ReactionEntity> createReaction(HttpServletRequest request, @RequestParam Long postId, @RequestBody ReactionDto reactionDto) {
        String token = request.getHeader(AUTHORIZATION).substring("Bearer ".length());
        return ResponseEntity.ok(reactionService.createReaction(jwtService.extractUsername(token), postId, reactionDto));
    }

    @PutMapping
    public ResponseEntity<ReactionEntity> updateReaction(HttpServletRequest request, @RequestParam Long postId, @RequestBody ReactionDto reactionDto) {
        String token = request.getHeader(AUTHORIZATION).substring("Bearer ".length());
        return ResponseEntity.ok(reactionService.updateReaction(jwtService.extractUsername(token), postId, reactionDto));
    }

    @DeleteMapping
    public ResponseEntity<ReactionEntity> deleteReaction(HttpServletRequest request, @RequestParam Long postId) {
        String token = request.getHeader(AUTHORIZATION).substring("Bearer ".length());
        return ResponseEntity.ok(reactionService.deleteReaction(jwtService.extractUsername(token), postId));
    }
}
