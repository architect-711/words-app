package edu.architect_711.words.entities.db;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Table(name = "language") @NoArgsConstructor @Data
public class LanguageEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    public LanguageEntity(String title) {
        this.title = title;
    }
}
