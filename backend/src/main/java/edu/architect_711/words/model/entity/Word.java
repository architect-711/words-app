package edu.architect_711.words.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Table(name = "person_words") @NoArgsConstructor @Data
public class Word {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    // cascade necessity is unknown as of now.
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id", nullable = false)
    private WordLanguage language;

    public Word(Person person, String title, WordLanguage language) {
        this.person = person;
        this.title = title;
        this.language = language;
    }
}