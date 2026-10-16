package com.example.mspin.client.model;

import lombok.Getter;

@Getter
public enum BucketType {
    PIN_IMAGES("pinterest-images"),
    PROFILE_IMAGES("profile-images");

    private final String bucketName;

    BucketType(String bucketName) {
        this.bucketName = bucketName;
    }
}
