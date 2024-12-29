package edu.architect_711.words.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity @Table(name = "person_words") @NoArgsConstructor @Data
public class Word {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false, name = "word_translation")
    private String wordTranslation;

    @Column(nullable = false, name = "word_description")
    private String wordDescription;

    // cascade necessity is unknown as of now.
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id", nullable = false)
    private WordLanguage language;

    @Column(columnDefinition = "timestamp")
    private LocalDateTime localDateTime;

    public Word(Person person, String title, String wordTranslation, String  wordDescription, WordLanguage language) {
        this.person = person;
        this.title = title;
        this.wordTranslation = wordTranslation;
        this.wordDescription = wordDescription;
        this.language = language;
        this.localDateTime = LocalDateTime.now(); 
    }
}