package edu.architect_711.words.repository;

import edu.architect_711.words.entities.db.WordEntity;
import edu.architect_711.words.entities.dto.BaseGroupDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WordRepository extends JpaRepository<WordEntity, Long> {
    @Query(
            nativeQuery = true,
            value = """
            select * from word
            limit :size
            offset :offset
            """
    )
    List<WordEntity> findAllPaginated(@Param("size") int size, @Param("offset") int offset);

    @Query(
            nativeQuery = true,
            value = """
            select * from word
            where title like '%:title%'
            limit :size
            offset :offset
            """
    )
    List<WordEntity> findByTitleApproximates(@Param("title") String title, @Param("size") int size, @Param("offset") int offset);

    default WordEntity safeFindWordById(Long id) throws EntityNotFoundException {
        return findById(id).orElseThrow(() -> new EntityNotFoundException("WordEntity not found with id: " + id));
    }

    @Query(
            nativeQuery = true, value = """
            select word.id,
                   word.title,
                   word.translation,
                   word.description,
                   word.language_id,
                   word.use_cases,
                   word.local_date_time
            from
                word
            inner join
                language
            on
                word.language_id = language.id
            where
                language.title like '%:lang%'
            limit
                :size
            offset
                :offset;
            """
    )
    List<WordEntity> findPaginatedByLangApproximately(@Param("lang") String lang,
                                                      @Param("size") int size, @Param("offset") int offset);

    @Query(
            nativeQuery = true,
            value = """
            select
                id, title
            from
                word_groups
            where
                :id = any (words_ids)
            """
    )
    List<BaseGroupDto> findWordGroupTitles(@Param("id") Long wordId);

}
