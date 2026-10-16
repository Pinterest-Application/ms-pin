package com.example.mspin.client.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MediaResponse {
    private UUID id;
    private String objectKey;
    private BucketType bucketType;
    private Integer width;
    private Integer height;
    private Boolean isActive;
}