package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "reactions")
public class ReactionEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Boolean reaction;

    @ManyToOne
    @JoinColumn(name = "joke_id")
    private JokeEntity joke;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

}