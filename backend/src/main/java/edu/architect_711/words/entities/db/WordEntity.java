package edu.architect_711.words.entities.db;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "word")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Type(ListArrayType.class)
    @Column(nullable = false, name = "translation", columnDefinition = "varchar[]")
    private List<String> translations;

    @Column(nullable = false, name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false)
    private LanguageEntity languageEntity;

    @Type(ListArrayType.class)
    @Column(
        name = "use_cases",
        columnDefinition = "text[]"
    )
    private List<String> useCases;

    @Column(columnDefinition = "timestamp")
    private LocalDateTime localDateTime;

    @Column(name = "transcription")
    private String transcription;

    public String getLanguage() {
        return languageEntity.getTitle();
    }
}