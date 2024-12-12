package edu.architect_711.words.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Table(name = "languages") @NoArgsConstructor @Data
public class WordLanguage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    public WordLanguage(String title) {
        this.title = title;
    }
}
