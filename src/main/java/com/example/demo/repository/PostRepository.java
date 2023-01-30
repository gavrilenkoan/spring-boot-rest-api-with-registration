package com.example.demo.repository;

import com.example.demo.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface PostRepository extends JpaRepository<PostEntity, Long> {

    Optional<PostEntity> findPostById(Long id);
}
