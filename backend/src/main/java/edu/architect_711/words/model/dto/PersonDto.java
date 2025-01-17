package edu.architect_711.words.model.dto;

import edu.architect_711.words.model.entity.Role;
import edu.architect_711.words.model.validation_groups.PersonValidationGroups;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class PersonDto {
    @Null(message = "Id must be null", groups = PersonValidationGroups.Create.class)
    @NotNull(message = "Id can't be null")
    private Long id;

    @NotBlank(message = "Username can't be blank")
    private String username;

    @NotBlank(message = "Password can't be blank")
    private String password;

    @Null(message = "Role must be null", groups = PersonValidationGroups.Create.class)
    @NotNull(message = "Role can't be blank")
    private Role role;
}
