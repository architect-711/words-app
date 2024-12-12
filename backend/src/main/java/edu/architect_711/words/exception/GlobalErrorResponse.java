package edu.architect_711.words.exception;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GlobalErrorResponse {
    @NotNull
    private LocalDateTime localDateTime;
    @NotBlank
    private String message;
    @NotBlank
    private String description;
}
