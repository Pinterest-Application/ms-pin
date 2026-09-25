package com.example.mspin.service;

import com.example.libexception.exception.ForbiddenException;
import com.example.libexception.exception.NotFoundException;
import com.example.mspin.config.CacheConfig;
import com.example.mspin.dto.CommentCreateRequest;
import com.example.mspin.dto.CommentResponse;
import com.example.mspin.entity.Comment;
import com.example.mspin.mapper.CommentMapper;
import com.example.mspin.repository.CommentRepository;
import com.example.mspin.repository.PinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PinRepository pinRepository;
    private final CommentMapper commentMapper;

    @Transactional
    public CommentResponse addComment(UUID pinId, UUID userId, CommentCreateRequest request) {
        if (!pinRepository.existsByIdAndIsActiveTrue(pinId)) {
            throw new NotFoundException("Active pin not found with id: " + pinId);
        }

        Comment comment = commentMapper.toEntity(request);
        comment.setPinId(pinId);
        comment.setUserId(userId);

        Comment savedComment = commentRepository.save(comment);
        pinRepository.incrementCommentCount(pinId);

        return commentMapper.toResponse(savedComment);
    }

    @Transactional(readOnly = true)
    @Cacheable(
            value = CacheConfig.PIN_COMMENTS_CACHE,
            key = "{#pinId, #pageable.pageNumber, #pageable.pageSize, #pageable.sort.toString()}"
    )
    public Page<CommentResponse> getCommentsByPinId(UUID pinId, Pageable pageable) {
        if (!pinRepository.existsByIdAndIsActiveTrue(pinId)) {
            throw new NotFoundException("Active pin not found with id: " + pinId);
        }

        return commentRepository.findAllByPinIdAndIsActiveTrue(pinId, pageable)
                .map(commentMapper::toResponse);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = CacheConfig.PIN_COMMENTS_CACHE, allEntries = true),
            @CacheEvict(value = CacheConfig.PIN_CACHE, allEntries = true)
    })
    public void deleteComment(UUID commentId, UUID userId) {
        Comment comment = commentRepository.findByIdAndIsActiveTrue(commentId)
                .orElseThrow(() -> new NotFoundException("Comment not found with id: " + commentId));

        if (!comment.getUserId().equals(userId)) {
            throw new ForbiddenException("You are not authorized to delete this comment");
        }

        comment.setIsActive(false);
        pinRepository.decrementCommentCount(comment.getPinId());
    }
}