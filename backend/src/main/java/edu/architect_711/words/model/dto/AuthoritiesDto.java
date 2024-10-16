package edu.architect_711.words.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class AuthoritiesDto {
    private Long userId;
    private String apiKey;
    private PersonAuthorities[] authorities;
    private PersonRole role;
}
