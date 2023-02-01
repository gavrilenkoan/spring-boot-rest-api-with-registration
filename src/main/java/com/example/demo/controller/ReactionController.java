package com.example.demo.controller;

import com.example.demo.config.JwtService;
import com.example.demo.dto.ReactionDto;
import com.example.demo.model.Reaction;
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
    public ResponseEntity<List<Reaction>> getAuthenticatedUserReactions(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION).substring("Bearer ".length());
        return ResponseEntity.ok(reactionService.getAuthenticatedUserReactions(jwtService.extractId(token)));
    }

    @PostMapping
    public ResponseEntity<Reaction> createReaction(HttpServletRequest request, @RequestParam Long postId,
                                                   @RequestBody ReactionDto reactionDto) {
        String token = request.getHeader(AUTHORIZATION).substring("Bearer ".length());
        return ResponseEntity.ok(reactionService.createReaction(jwtService.extractId(token), postId, reactionDto));
    }

    @PutMapping
    public ResponseEntity<Reaction> updateReaction(HttpServletRequest request, @RequestParam Long postId,
                                                   @RequestBody ReactionDto reactionDto) {
        String token = request.getHeader(AUTHORIZATION).substring("Bearer ".length());
        return ResponseEntity.ok(reactionService.updateReaction(jwtService.extractId(token), postId, reactionDto));
    }

    @DeleteMapping
    public ResponseEntity<Reaction> deleteReaction(HttpServletRequest request, @RequestParam Long postId) {
        String token = request.getHeader(AUTHORIZATION).substring("Bearer ".length());
        return ResponseEntity.ok(reactionService.deleteReaction(jwtService.extractId(token), postId));
    }
}
