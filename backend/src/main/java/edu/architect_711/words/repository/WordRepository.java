package edu.architect_711.words.repository;

import edu.architect_711.words.entities.db.WordEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WordRepository extends JpaRepository<WordEntity, Long> {
    Optional<WordEntity> findByTitle(final String title);

    @Query(
            nativeQuery = true,
            value = """
                    SELECT * FROM word
                    WHERE title LIKE %:title%
                    """
    )
    List<WordEntity> findByTitleApproximates(final String title);

    default WordEntity safeFindWordById(Long id) {
        return findById(id).orElseThrow(() -> new EntityNotFoundException("WordEntity not found with id: " + id));
    }

    default WordEntity safeFindWordByTitle(final String title) {
        return findByTitle(title).orElseThrow(() -> new EntityNotFoundException("Word not found with title: " + title));
    }

    @Query(nativeQuery = true, value = """
            SELECT * FROM word
            WHERE account_id = :id
            LIMIT :limit OFFSET :offset
            """)
    List<WordEntity> findAllPaginatedById(Long id, Long limit, Long offset);
}
