package com.example.demo.repository;

import com.example.demo.entity.ReactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface ReactionRepository extends JpaRepository<ReactionEntity, Long> {

    Optional<ReactionEntity> findReactionByUserIdAndPostId(Long userId, Long postId);
}
