package com.example.mspin.service;

import com.example.libexception.exception.ConflictException;
import com.example.libexception.exception.NotFoundException;
import com.example.mspin.client.MsMediaClient;
import com.example.mspin.client.model.MediaResponse;
import com.example.mspin.config.CacheConfig;
import com.example.mspin.dto.PinCreateRequest;
import com.example.mspin.dto.PinResponse;
import com.example.mspin.dto.event.PinImageProcessEvent;
import com.example.mspin.entity.Pin;
import com.example.mspin.entity.PinLike;
import com.example.mspin.mapper.PinMapper;
import com.example.mspin.producer.KafkaProducerService;
import com.example.mspin.repository.PinLikeRepository;
import com.example.mspin.repository.PinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PinService {

    private final PinRepository pinRepository;
    private final PinLikeRepository pinLikeRepository;
    private final PinMapper pinMapper;
    private final MsMediaClient msMediaClient;
    private final KafkaProducerService kafkaProducerService;

    @Value("${spring.kafka.topics.pin-image-process}")
    private String pinProcessTopic;

    @Transactional
    public PinResponse create(PinCreateRequest request, UUID userId) {
        // todo media id check
        MediaResponse mediaResponse = msMediaClient.getById(request.getMediaId()); // get media

        Pin pin = pinMapper.toEntity(request);
        pin.setUserId(userId);
        Pin savedPin = pinRepository.save(pin); // pin save

        PinImageProcessEvent event = PinImageProcessEvent.builder()
                .pinId(savedPin.getId())
                .objectKey(mediaResponse.getObjectKey())
                .bucketName(mediaResponse.getBucketType().getBucketName())
                .mediaWidth(mediaResponse.getWidth())
                .mediaHeight(mediaResponse.getHeight())
                .build(); // build event model

        kafkaProducerService.send(pinProcessTopic, savedPin.getId().toString(), event); // send kafka

        return pinMapper.toResponse(savedPin);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = CacheConfig.PIN_CACHE, key = "#id")
    public PinResponse getById(UUID id) {
        Pin pin = pinRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new NotFoundException("Pin not found with id: " + id));
        return pinMapper.toResponse(pin);
    }

    @Transactional
    @CacheEvict(value = CacheConfig.PIN_CACHE, key = "#pinId")
    public void likePin(UUID pinId, UUID userId) {
        if (!pinRepository.existsByIdAndIsActiveTrue(pinId)) {
            throw new NotFoundException("Active pin not found with id: " + pinId);
        }

        if (pinLikeRepository.existsByPinIdAndUserId(pinId, userId)) {
            // todo like geri cek
            throw new ConflictException("Pin already liked by user: " + userId);
        }

        PinLike pinLike = PinLike.builder()
                .pinId(pinId)
                .userId(userId)
                .build();

        pinLikeRepository.save(pinLike);
        pinRepository.incrementLikeCount(pinId);
    }
}