package com.example.mspin.mapper;

import com.example.mspin.dto.CommentCreateRequest;
import com.example.mspin.dto.CommentResponse;
import com.example.mspin.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pinId", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Comment toEntity(CommentCreateRequest request);

    CommentResponse toResponse(Comment comment);
}