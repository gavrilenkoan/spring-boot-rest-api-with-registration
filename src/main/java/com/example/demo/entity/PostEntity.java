package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "posts")
@NoArgsConstructor
public class PostEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String title;

    private String text;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "post")
    private List<ReactionEntity> reactions;

    public PostEntity(String title, String text, UserEntity user) {
        this.title = title;
        this.text = text;
        this.user = user;
        this.reactions = List.of();
    }
}