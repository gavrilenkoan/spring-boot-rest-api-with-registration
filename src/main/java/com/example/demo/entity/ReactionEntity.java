package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "reactions")
@NoArgsConstructor
public class ReactionEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Boolean reaction;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public ReactionEntity(Boolean reaction, PostEntity post, UserEntity user) {
        this.reaction = reaction;
        this.post = post;
        this.user = user;
    }
}