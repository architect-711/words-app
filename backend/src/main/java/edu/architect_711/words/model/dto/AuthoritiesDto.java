package edu.architect_711.words.model.dto;

import edu.architect_711.words.model.entity.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class AuthoritiesDto {
    private Person userId;
    private String apiKey;
    private PersonAuthorities[] authorities;
    private PersonRole role;
}
