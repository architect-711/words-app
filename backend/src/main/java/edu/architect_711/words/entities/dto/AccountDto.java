package edu.architect_711.words.entities.dto;

import edu.architect_711.words.entities.db.Role;
import edu.architect_711.words.entities.Account;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class AccountDto implements Account {
    @NotBlank(message = "Username can't be blank")
    private String username;

    @NotBlank(message = "Password can't be blank")
    private String password;

    @NotBlank(message = "Role can't be blank")
    private Role role;
}
