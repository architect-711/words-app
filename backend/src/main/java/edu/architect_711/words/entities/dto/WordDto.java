package edu.architect_711.words.entities.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
public class WordDto {
    @Null(message = "Id must be null.", groups = OnCreate.class)
    @NotNull(message = "Id can't be null", groups = OnCreated.class)
    private Long id;

    @NotBlank(message = "Title cannot be blank", groups = Default.class)
    private String title;

    @NotNull(message = "Translations array cannot be null.", groups = Default.class)
    @NotEmpty(message = "Translation list cannot be empty", groups = Default.class) // TODO this might cause exceptions
    private List<@NotBlank String> translations;

    private String description;
    private List<String> useCases;

    @NotBlank(message = "LanguageEntity cannot be blank", groups = Default.class)
    private String language;

    @Null(message = "Local date time must be null", groups = OnCreate.class)
    @NotNull(message = "Time created cannot be blank", groups = OnCreated.class)
    private LocalDateTime localDateTime;

    private String transcription;

    public interface OnCreate {}
    public interface OnCreated {}
}
