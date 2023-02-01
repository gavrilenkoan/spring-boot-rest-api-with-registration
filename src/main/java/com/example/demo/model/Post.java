package com.example.demo.model;

import com.example.demo.entity.PostEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class Post {

    private Long id;
    private String title;
    private String text;
    private List<Reaction> reactions;

    public static Post toModel(PostEntity entity) {
        Post model = new Post();
        model.setId(entity.getId());
        model.setTitle(entity.getTitle());
        model.setText(entity.getText());
        model.setReactions(entity.getReactions().stream().map(Reaction::toModel).collect(Collectors.toList()));
        return model;
    }
}

