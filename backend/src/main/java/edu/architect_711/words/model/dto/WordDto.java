package edu.architect_711.words.model.dto;

import edu.architect_711.words.model.validation_groups.WordValidationGroups;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class WordDto {
    @NotNull(message = "Id can't be null", groups = WordValidationGroups.Create.class)
    private Long id;

    @NotNull(message = "User id can't be null", groups = WordValidationGroups.Update.class)
    private Long userId;

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "Language cannot be blank")
    private String language;
}
