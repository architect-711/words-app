package edu.architect_711.words.repository;

import edu.architect_711.words.entities.db.WordEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WordRepository extends JpaRepository<WordEntity, Long> {
    Optional<WordEntity> findByTitle(final String title);

    @Query(
            nativeQuery = true,
            value = """
                    SELECT * FROM word
                    WHERE title LIKE %:title%
                    LIMIT :size OFFSET :page
                    """
    )
    List<WordEntity> findByTitleApproximates(final String title, final int size, final int page);

    default WordEntity safeFindWordById(Long id) {
        return findById(id).orElseThrow(() -> new EntityNotFoundException("WordEntity not found with id: " + id));
    }

    @Query(nativeQuery = true, value = """
            SELECT * FROM word
            WHERE account_id = :id
            LIMIT :limit OFFSET :offset
            """)
    List<WordEntity> findAllPaginatedById(Long id, Long limit, Long offset);
// TODO fix naming, page -> offset
    @Query(
            nativeQuery = true, value = """
            SELECT word.id,
                   word.title,
                   word.translation,
                   word.description,
                   word.language_id,
                   word.use_cases,
                   word.local_date_time
            FROM word
            INNER JOIN language
            ON word.language_id = language.id
            WHERE language.title LIKE %:lang%
            LIMIT :size OFFSET :page;
            """
    )
    List<WordEntity> findPaginatedByLangAprx(@Param("lang") String lang, 
               @Param("size") int size, @Param("page") int page);

}
