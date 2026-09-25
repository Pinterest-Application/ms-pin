package com.example.mspin.mapper;

import com.example.mspin.dto.PinCreateRequest;
import com.example.mspin.dto.PinResponse;
import com.example.mspin.entity.Pin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PinMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "likeCount", ignore = true)
    @Mapping(target = "commentCount", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Pin toEntity(PinCreateRequest request);

    PinResponse toResponse(Pin pin);
}