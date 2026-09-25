package com.example.mspin.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "pin_like", uniqueConstraints = {
        @UniqueConstraint(name = "uq_pin_like_pin_user",
                columnNames = {"pin_id", "user_id"})
}
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PinLike {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "pin_id", nullable = false)
    private UUID pinId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;
}