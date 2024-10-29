package edu.architect_711.words.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class WordDto {
    private Long id;
    private Long userId;
    private String title;
    private WordLanguage language;
}
