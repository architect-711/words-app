package edu.architect_711.words.entities.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data @AllArgsConstructor
@NoArgsConstructor
public class GroupDto {
    @NotNull(message = "Id cannot be null", groups = OnCreated.class)
    @Null(message = "Id must be null", groups = OnCreate.class)
    private Long id;

    @NotNull(message = "Title cannot be null", groups = Default.class)
    private String title;

    private String description;
    @Nullable
    private List<Long> wordsIds;

    @NotNull(message = "Creation date can be null", groups = OnCreated.class)
    @Null(message = "Creation date must be null", groups = OnCreate.class)
    private LocalDateTime created;

    public interface OnCreate {}
    public interface OnCreated {}
}
