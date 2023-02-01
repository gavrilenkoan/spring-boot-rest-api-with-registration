package com.example.demo.service;

import com.example.demo.entity.PostEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.entity.UserRole;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final UserService userService;
    private final PostService postService;

    public String deleteUser(Long id) {
        UserEntity user = userRepository.findUserById(id)
                .orElseThrow(() -> new IllegalStateException("user with id " + id + "not found"));

        if (user.getRole() == UserRole.ROLE_ADMIN || user.getRole() == UserRole.ROLE_SUPER_ADMIN) {
            throw new IllegalStateException("you can not delete user with role admin");
        }
        userService.deleteUser(id);
        return "deleted successfully";
    }

    public String deletePost(Long postId) {
        PostEntity post = postRepository.findPostById(postId)
                .orElseThrow(() -> new IllegalStateException("post with id " + postId + "not found"));

        if (post.getUser().getRole() == UserRole.ROLE_ADMIN || post.getUser().getRole() == UserRole.ROLE_SUPER_ADMIN) {
            throw new IllegalStateException("you can not delete post of user with role admin");
        }
        postService.deletePost(post.getUser().getId(), postId);
        return "deleted successfully";
    }
}
