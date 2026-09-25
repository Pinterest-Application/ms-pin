package com.example.mspin.repository;

import com.example.mspin.entity.PinLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PinLikeRepository extends JpaRepository<PinLike, UUID> {

    boolean existsByPinIdAndUserId(UUID pinId, UUID userId);

    void deleteByPinIdAndUserId(UUID pinId, UUID userId);
}