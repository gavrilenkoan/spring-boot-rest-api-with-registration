package com.example.demo.service;

import com.example.demo.dto.ReactionDto;
import com.example.demo.entity.PostEntity;
import com.example.demo.entity.ReactionEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.ReactionRepository;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReactionService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ReactionRepository reactionRepository;

    public List<ReactionEntity> getReactions(String email) {
        UserEntity user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(("user with email " + email + "not found")));

        return user.getReactions();
    }

    public ReactionEntity createReaction(String email, Long postId, ReactionDto reactionDto) {
        UserEntity user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(("user with email " + email + "not found")));
        PostEntity post = postRepository.findPostById(postId)
                .orElseThrow(() -> new UsernameNotFoundException(("post with id " + postId + "not found")));

        ReactionEntity reactionEntity = new ReactionEntity(reactionDto.getReaction(), post, user);

        post.getReactions().add(reactionEntity);
        user.getReactions().add(reactionEntity);
        reactionRepository.save(reactionEntity);
        return reactionEntity;
    }
}
