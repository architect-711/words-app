package edu.architect_711.words.entities.dto;

import edu.architect_711.words.entities.Account;
import edu.architect_711.words.entities.db.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class AccountDto implements Account {
    @NotBlank(message = "Username can't be blank", groups = Default.class)
    private String username;

    @NotBlank(message = "Password can't be blank", groups = Default.class)
    private String password;

    @Null(message = "The role must be null", groups = OnCreate.class)
    @NotNull(message = "Role can't be blank", groups = OnCreated.class)
    private Role role;

    public interface OnCreate {}
    public interface OnCreated {}
}
