package edu.architect_711.words.entities.db;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.List;

@Entity @NoArgsConstructor @Data
@Table(name = "word_groups")
public class GroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String title;
    @Column(nullable = true)
    private String description;

    @Type(ListArrayType.class)
    @Column(nullable = true, name = "words_ids", columnDefinition = "bigint[]")
    private List<Long> wordsIds;

    @Column(columnDefinition = "timestamp")
    private LocalDateTime created;

    public GroupEntity(
            String title,
            String description,
            List<Long> wordsIds,
            LocalDateTime created
    ) {
        this.title = title;
        this.description = description;
        this.wordsIds = wordsIds;
        this.created = created; // TODO REVERT IF THAT SHIT DOESN'T SET THE current_timestamp
    }

}
