package edu.architect_711.words.entities.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponseDto {
    @NotNull
    private LocalDateTime localDateTime;
    @NotBlank
    private String message;
    @NotBlank
    private String description;
    @NonNull
    private Integer status;
}
