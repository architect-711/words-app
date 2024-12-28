package edu.architect_711.words.model.dto;

import edu.architect_711.words.model.validation_groups.WordValidationGroups;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * <h1>What a hell is going on?</h1>
 * <br> <br>
 * There are fields: id, userId, title, wordTranslation, language, localDateTime
 * <br> <br>
 * When called the `create` method in service, the only fields `id` and `localDateTime` can be null
 * <br>
 * When called the `update` method in service, the only field `userId` can be null
 * */
@Data @AllArgsConstructor @NoArgsConstructor
public class WordDto {
    @Null(
            message = "Id must be null.",
            groups = WordValidationGroups.Create.class
    )
    @NotNull(
            message = "Id can't be null",
            groups = WordValidationGroups.Update.class
    )
    private Long id;

    @Null(
            message = "User id must be null",
            groups = WordValidationGroups.Update.class
    )
    @NotNull(
            message = "User id can't be null",
            groups = WordValidationGroups.Create.class
    )
    private Long userId;

    @NotBlank(message = "Title cannot be blank", groups = {
                WordValidationGroups.Create.class,
                WordValidationGroups.Update.class
    })
    private String title;

    @NotBlank(message = "Word translation cannot be blank.", groups = {
            WordValidationGroups.Create.class,
            WordValidationGroups.Update.class
    })
    private String wordTranslation;

    @NotBlank(message = "Language cannot be blank", groups = {
            WordValidationGroups.Create.class,
            WordValidationGroups.Update.class
    })
    private String language;

    @Null(
            message = "Date must be null",
            groups = WordValidationGroups.Create.class
    )
    @NotNull(
            message = "Date can't be null",
            groups = WordValidationGroups.Update.class
    )
    private LocalDateTime localDateTime;
}
