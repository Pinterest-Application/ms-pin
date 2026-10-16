package com.example.mspin.client;

import com.example.mspin.client.model.MediaResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "ms-media")
public interface MsMediaClient {

    @GetMapping("/{id}")
    MediaResponse getById(@PathVariable("id") UUID id);
}
