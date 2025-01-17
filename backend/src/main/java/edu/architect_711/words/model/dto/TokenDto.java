package edu.architect_711.words.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TokenDto(
        @NotBlank(message = "Access token can't be blank")
        String accessToken,
        @NotBlank(message = "Refresh token can't be blank")
        String refreshToken,
        @NotNull(message = "Is logged out can't be null")
        boolean isLoggedOut
) {}