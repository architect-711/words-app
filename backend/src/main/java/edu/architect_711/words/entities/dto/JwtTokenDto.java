package edu.architect_711.words.entities.dto;

import edu.architect_711.words.entities.JwtToken;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class JwtTokenDto implements JwtToken {
        @NotBlank(message = "Access token can't be blank")
        private String accessToken;
        @NotBlank(message = "Refresh token can't be blank")
        private String refreshToken;
}