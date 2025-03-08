package edu.architect_711.words.entities.dto;

import edu.architect_711.words.entities.Word;
import edu.architect_711.words.entities.validation_groups.WordValidationGroups;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @AllArgsConstructor @NoArgsConstructor
public class WordDto implements Word  {
    @Null(message = "Id must be null.", groups = WordValidationGroups.Create.class)
    @NotNull(message = "Id can't be null")
    private Long id;

    @NotNull(message = "User id can't be null")
    private Long userId;

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "WordEntity translation cannot be blank.")
    private String translation;

    @NotBlank(message = "WordEntity description cannot be blank.")
    private String description;

    @NotBlank(message = "LanguageEntity cannot be blank")
    private String language;

    @Null(message = "Local date time must be null", groups = WordValidationGroups.Create.class)
    @NotNull(message = "Time created cannot be blank")
    private LocalDateTime localDateTime;
}
