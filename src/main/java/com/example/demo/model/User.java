package com.example.demo.model;

import com.example.demo.entity.UserEntity;
import com.example.demo.entity.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class User {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private UserRole role;
    private List<Post> posts;
    private List<Reaction> reactions;

    public static User toModel(UserEntity entity) {
        User model = new User();
        model.setId(entity.getId());
        model.setFirstname(entity.getFirstname());
        model.setLastname(entity.getLastname());
        model.setEmail(entity.getEmail());
        model.setRole(entity.getRole());
        model.setPosts(entity.getPosts().stream().map(Post::toModel).collect(Collectors.toList()));
        model.setReactions(entity.getReactions().stream().map(Reaction::toModel).collect(Collectors.toList()));
        return model;
    }
}
