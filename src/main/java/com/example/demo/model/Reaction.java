package com.example.demo.model;

import com.example.demo.entity.ReactionEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Reaction {

    private Long postId;
    private String toUserEmail;
    private String fromUserEmail;
    private Boolean reaction;

    public static Reaction toModel(ReactionEntity entity) {
        Reaction model = new Reaction();
        model.setReaction(entity.getReaction());
        model.setPostId(entity.getPost().getId());
        model.setToUserEmail(entity.getPost().getUser().getEmail());
        model.setFromUserEmail(entity.getUser().getEmail());
        return model;
    }}
