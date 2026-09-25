package com.example.mspin.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfig {

    public static final String PIN_CACHE = "pins";
    public static final String PIN_COMMENTS_CACHE = "pinComments";

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();

        cacheManager.registerCustomCache(PIN_CACHE,
                Caffeine.newBuilder()
                        .maximumSize(5000)
                        .expireAfterWrite(Duration.ofMinutes(10))
                        .recordStats()
                        .build());

        cacheManager.registerCustomCache(PIN_COMMENTS_CACHE,
                Caffeine.newBuilder()
                        .maximumSize(2000)
                        .expireAfterWrite(Duration.ofMinutes(5))
                        .recordStats()
                        .build());

        return cacheManager;
    }
}