package com.example.mspin.repository;

import com.example.mspin.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {

    Optional<Comment> findByIdAndIsActiveTrue(UUID id);

    Page<Comment> findAllByPinIdAndIsActiveTrue(UUID pinId, Pageable pageable);
}