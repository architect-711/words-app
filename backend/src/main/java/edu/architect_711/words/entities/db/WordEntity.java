package edu.architect_711.words.entities.db;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import edu.architect_711.words.entities.Word;

@Entity
@Table(name = "word")
@NoArgsConstructor
@Data
public class WordEntity implements Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false, name = "translation")
    private String translation;

    @Column(nullable = false, name = "description")
    private String description;

    // TODO cascade necessity is unknown as of now. (???)
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private AccountEntity accountEntity;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id", nullable = false)
    private LanguageEntity languageEntity;

    @Column(columnDefinition = "timestamp")
    private LocalDateTime localDateTime;

    public WordEntity(
            AccountEntity accountEntity,
            String title,
            String translation,
            String description,
            LanguageEntity languageEntity) {
        this.accountEntity = accountEntity;
        this.title = title;
        this.translation = translation;
        this.description = description;
        this.languageEntity = languageEntity;
        this.localDateTime = LocalDateTime.now();
    }

    @Override
    public Long getUserId() {
        return accountEntity.getId();
    }

    @Override
    public String getLanguage() {
        return languageEntity.getTitle();
    }
}