package edu.architect_711.words.entities.dto;

import edu.architect_711.words.entities.Word;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @AllArgsConstructor @NoArgsConstructor
public class WordDto implements Word  {
    @Null(message = "Id must be null.", groups = OnCreate.class)
    @NotNull(message = "Id can't be null", groups = OnCreated.class)
    private Long id;

    @NotBlank(message = "Title cannot be blank", groups = Default.class)
    private String title;

    @NotBlank(message = "WordEntity translation cannot be blank.", groups = Default.class)
    private String translation;

    private String description;

    @NotBlank(message = "LanguageEntity cannot be blank", groups = Default.class)
    private String language;

    @Null(message = "Local date time must be null", groups = OnCreate.class)
    @NotNull(message = "Time created cannot be blank", groups = OnCreated.class)
    private LocalDateTime localDateTime;

    public interface OnCreate {}
    public interface OnCreated {}
}
