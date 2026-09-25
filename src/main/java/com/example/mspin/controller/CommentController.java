package com.example.mspin.controller;

import com.example.mspin.dto.CommentCreateRequest;
import com.example.mspin.dto.CommentResponse;
import com.example.mspin.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{pinId}")
    public ResponseEntity<CommentResponse> addComment(
            @PathVariable UUID pinId,
            @Valid @RequestBody CommentCreateRequest request,
            @AuthenticationPrincipal Jwt jwt) {
        CommentResponse response = commentService.addComment(pinId,
                UUID.fromString(Objects.requireNonNull(jwt.getSubject())), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{pinId}")
    public ResponseEntity<Page<CommentResponse>> getCommentsByPinId(
            @PathVariable UUID pinId,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<CommentResponse> comments = commentService.getCommentsByPinId(pinId, pageable);
        return ResponseEntity.ok(comments);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable UUID id, @AuthenticationPrincipal Jwt jwt) {
        commentService.deleteComment(id, UUID.fromString(Objects.requireNonNull(jwt.getSubject())));
        return ResponseEntity.noContent().build();
    }
}