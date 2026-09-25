package com.example.mspin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PinCreateRequest {

    @NotNull(message = "mediaId must not be null")
    private UUID mediaId;

    @NotBlank(message = "title must not be blank")
    @Size(max = 150, message = "title must not exceed 150 characters")
    private String title;

    @Size(max = 2000, message = "description must not exceed 2000 characters")
    private String description;
}
