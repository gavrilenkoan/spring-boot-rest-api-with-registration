package com.example.demo.service;

import com.example.demo.dto.ReactionDto;
import com.example.demo.entity.PostEntity;
import com.example.demo.entity.ReactionEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.model.Reaction;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.ReactionRepository;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReactionService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ReactionRepository reactionRepository;

    private ReactionEntity findReactionByUserIdAndPostId(Long userId, Long postId) {
        UserEntity user = userRepository.findUserById(userId)
                .orElseThrow(() -> new IllegalStateException(("user with email " + userId + "not found")));

        if (reactionRepository.findReactionByUserIdAndPostId(user.getId(), postId).isEmpty()) {
            throw new IllegalStateException();
        }

        return reactionRepository.findReactionByUserIdAndPostId(user.getId(), postId)
                .orElseThrow(IllegalStateException::new);
    }


    public List<Reaction> getAuthenticatedUserReactions(Long userId) {
        UserEntity user = userRepository.findUserById(userId)
                .orElseThrow(() -> new IllegalStateException(("user with email " + userId + "not found")));

        return user.getReactions().stream().map(Reaction::toModel).collect(Collectors.toList());
    }

    public Reaction createReaction(Long userId, Long postId, ReactionDto reactionDto) {
        UserEntity user = userRepository.findUserById(userId)
                .orElseThrow(() -> new IllegalStateException(("user with email " + userId + "not found")));
        PostEntity post = postRepository.findPostById(postId)
                .orElseThrow(() -> new IllegalStateException(("post with id " + postId + "not found")));

        if (reactionRepository.findReactionByUserIdAndPostId(user.getId(), postId).isPresent()) {
            throw new IllegalStateException();
        }

        ReactionEntity reaction = new ReactionEntity(reactionDto.getReaction(), post, user);

        post.getReactions().add(reaction);
        user.getReactions().add(reaction);
        reactionRepository.save(reaction);
        return Reaction.toModel(reaction);
    }

    @Transactional
    public Reaction updateReaction(Long userId, Long postId, ReactionDto reactionDto) {
        ReactionEntity reaction = findReactionByUserIdAndPostId(userId, postId);
        if (reactionDto.getReaction() != null) {
            reaction.setReaction(reactionDto.getReaction());
        }
        return Reaction.toModel(reaction);
    }

    public Reaction deleteReaction(Long userId, Long postId) {
        ReactionEntity reaction = findReactionByUserIdAndPostId(userId, postId);
        reactionRepository.delete(reaction);
        return Reaction.toModel(reaction);
    }
}
