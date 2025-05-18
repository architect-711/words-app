package edu.architect_711.words.repository;

import edu.architect_711.words.entities.db.WordEntity;
import edu.architect_711.words.entities.dto.BaseGroupDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static edu.architect_711.words.repository.WordSpecifications.containingTitle;

public interface WordRepository extends JpaRepository<WordEntity, Long>, JpaSpecificationExecutor<WordEntity> {
    @Query(
            nativeQuery = true,
            value = """
            select * from word
            limit :size
            offset :offset
            """
    )
    List<WordEntity> findAllPaginated(@Param("size") int size, @Param("offset") int offset);

    default WordEntity safeFindWordById(Long id) throws EntityNotFoundException {
        return findById(id).orElseThrow(() -> new EntityNotFoundException("WordEntity not found with id: " + id));
    }

    @Query(
            nativeQuery = true, value = """
            select
                word.*
            from
                word
            inner join
                language
            on
                word.language_id = language.id
            where
                language.title like :lang
            limit
                :size
            offset
                :offset;
            """
    )
    List<WordEntity> findAllByLanguagePaginated(@Param("lang") String lang,
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

    default List<WordEntity> findAllByTitlePaginated(String title, int size, int page) {
        return findAll(containingTitle(title), PageRequest.of(page, size)).getContent();
    }

}
