package com.example.mspin.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PinImageProcessEvent {
    private UUID pinId;
    private String mediaUrl;
    private Integer mediaWidth;
    private Integer mediaHeight;
}