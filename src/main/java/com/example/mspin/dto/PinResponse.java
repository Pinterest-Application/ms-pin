package com.example.mspin.dto;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PinResponse {

    private UUID id;
    private UUID userId;
    private UUID mediaId;
    private String title;
    private String description;
    private Long likeCount;
    private Long commentCount;
    private Boolean isActive;
    private Instant createdAt;
}
