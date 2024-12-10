package edu.architect_711.words.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class WordLanguageDto {
    @NotBlank(message = "Title cannot be blank")
    private String title;
}
