package com.example.demo.service;

import com.example.demo.dto.PostDto;
import com.example.demo.entity.PostEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.model.Post;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.ReactionRepository;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ReactionRepository reactionRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll().stream().map(Post::toModel).collect(Collectors.toList());
    }

    public List<Post> getUsersPosts(Long userId) {
        UserEntity user = userRepository.findUserById(userId)
                .orElseThrow(() -> new UsernameNotFoundException(("user with email " + userId + "not found")));
        return user.getPosts().stream().map(Post::toModel).collect(Collectors.toList());
    }

    public Post createPost(Long userId, PostDto postDto) {
        UserEntity user = userRepository.findUserById(userId)
                .orElseThrow(() -> new IllegalStateException("user with id " + userId + "not found"));
        PostEntity post = new PostEntity(postDto.getTitle(), postDto.getText(), user);
        user.getPosts().add(post);
        postRepository.save(post);
        return Post.toModel(post);
    }

    public Post getPost(Long postId) {
        return Post.toModel(postRepository.findPostById(postId)
                .orElseThrow(() -> new IllegalStateException(("post with id " + postId + "not found"))));
    }

    @Transactional
    public Post updatePost(Long userId, Long postId, PostDto postDto) {
        UserEntity user = userRepository.findUserById(userId)
                .orElseThrow(() -> new IllegalStateException("user with id " + userId + "not found"));
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
        return Post.toModel(post);
    }

    public String deletePost(Long userId, Long postId) {
        UserEntity user = userRepository.findUserById(userId)
                .orElseThrow(() -> new IllegalStateException("user with id " + userId + "not found"));
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
