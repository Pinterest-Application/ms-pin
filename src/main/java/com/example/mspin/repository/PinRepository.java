package com.example.mspin.repository;

import com.example.mspin.entity.Pin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PinRepository extends JpaRepository<Pin, UUID> {

    Optional<Pin> findByIdAndIsActiveTrue(UUID id);

    boolean existsByIdAndIsActiveTrue(UUID id);

    @Modifying
    @Query("UPDATE Pin p SET p.likeCount = p.likeCount + 1 WHERE p.id = :pinId")
    void incrementLikeCount(@Param("pinId") UUID pinId);

    @Modifying
    @Query("UPDATE Pin p SET p.likeCount = p.likeCount - 1 WHERE p.id = :pinId AND p.likeCount > 0")
    void decrementLikeCount(@Param("pinId") UUID pinId);

    @Modifying
    @Query("UPDATE Pin p SET p.commentCount = p.commentCount + 1 WHERE p.id = :pinId")
    void incrementCommentCount(@Param("pinId") UUID pinId);

    @Modifying
    @Query("UPDATE Pin p SET p.commentCount = p.commentCount - 1 WHERE p.id = :pinId AND p.commentCount > 0")
    void decrementCommentCount(@Param("pinId") UUID pinId);
}
