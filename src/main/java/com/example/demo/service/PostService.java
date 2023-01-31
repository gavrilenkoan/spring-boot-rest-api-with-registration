package com.example.demo.service;

import com.example.demo.dto.PostDto;
import com.example.demo.entity.PostEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.ReactionRepository;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ReactionRepository reactionRepository;

    public List<PostEntity> getAllPosts() {
        return postRepository.findAll();
    }

    public List<PostEntity> getAuthenticatedUsersPosts(String email) {
        UserEntity user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(("user with email " + email + "not found")));
        return user.getPosts();
    }

    public List<PostEntity> getUsersPosts(Long userId) {
        UserEntity user = userRepository.findUserById(userId)
                .orElseThrow(() -> new UsernameNotFoundException(("user with email " + userId + "not found")));
        return user.getPosts();
    }

    public PostEntity createPost(String email, PostDto postDto) {
        UserEntity user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(("user with email " + email + "not found")));
        PostEntity post = new PostEntity(postDto.getTitle(), postDto.getText(), user);
        user.getPosts().add(post);
        postRepository.save(post);
        return post;
    }

    public PostEntity getPost(Long postId) {
        return postRepository.findPostById(postId)
                .orElseThrow(() -> new IllegalStateException(("post with id " + postId + "not found")));
    }

    @Transactional
    public PostEntity updatePost(String email, Long postId, PostDto postDto) {
        UserEntity user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(("user with email " + email + "not found")));
        PostEntity post = postRepository.findPostById(postId)
                .orElseThrow(() -> new IllegalStateException(("post with id " + postId + "not found")));
        if (post.getUser() != user) {
            throw new IllegalStateException();
        }
        if (postDto.getTitle() != null && !Objects.equals(postDto.getTitle(), "")) {
            post.setTitle(postDto.getTitle());
        }
        if (postDto.getText() != null && !Objects.equals(postDto.getText(), "")) {
            post.setText(postDto.getText());
        }
        return post;
    }

    public String deletePost(String email, Long postId) {
        UserEntity user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(("user with email " + email + "not found")));
        PostEntity post = postRepository.findPostById(postId)
                .orElseThrow(() -> new IllegalStateException(("post with id " + postId + "not found")));
        if (post.getUser() != user) {
            throw new IllegalStateException();
        }

        reactionRepository.deleteAll(reactionRepository.findAll().stream().filter(r -> r.getPost() == post).toList());
        postRepository.deleteById(postId);
        return "deleted successfully";
    }
}
