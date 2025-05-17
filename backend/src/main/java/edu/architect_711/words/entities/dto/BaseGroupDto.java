package edu.architect_711.words.entities.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseGroupDto {
    @NotNull(message = "The id cannot be null")
    private Long id;
    @NotNull(message = "The title cannot be null")
    private String title;
}
