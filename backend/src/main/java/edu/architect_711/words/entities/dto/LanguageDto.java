package edu.architect_711.words.entities.dto;

import edu.architect_711.words.entities.Language;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class LanguageDto implements Language {
    @NotBlank(message = "Title cannot be blank")
    private String title;
}
