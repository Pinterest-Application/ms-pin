package com.example.mspin.controller;

import com.example.mspin.dto.PinCreateRequest;
import com.example.mspin.dto.PinResponse;
import com.example.mspin.service.PinService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/pins")
@RequiredArgsConstructor
public class PinController {

    private final PinService pinService;

    @PostMapping
    public ResponseEntity<PinResponse> create(@Valid @RequestBody PinCreateRequest request,
                                              @AuthenticationPrincipal Jwt jwt) {
        PinResponse response = pinService.create(request, UUID.fromString(Objects.requireNonNull(jwt.getSubject())));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PinResponse> getById(@PathVariable UUID id) {
        PinResponse response = pinService.getById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Void> like(@PathVariable UUID id, @AuthenticationPrincipal Jwt jwt) {
        pinService.likePin(id, UUID.fromString(Objects.requireNonNull(jwt.getSubject())));
        return ResponseEntity.noContent().build();
    }
}