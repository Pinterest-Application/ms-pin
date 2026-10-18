package com.example.mspin.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MediaUrlUtil {

    private final String baseUrl;

    public MediaUrlUtil(
            @Value("${media.url.base}") String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String generateUrl(String bucketName, String objectKey) {
        return "%s/%s/%s".formatted(
                baseUrl.replaceAll("/+$", ""),
                bucketName.replaceAll("^/+|/+$", ""),
                objectKey.replaceAll("^/+", "")
        );
    }
}
