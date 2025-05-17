package edu.architect_711.words.entities.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data @AllArgsConstructor @NoArgsConstructor
public class LanguageDto {
    @NonNull @Min(0)
    private Long id;

    @NotBlank(message = "Title cannot be blank")
    private String title;
}
