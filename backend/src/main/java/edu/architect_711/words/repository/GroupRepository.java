package edu.architect_711.words.repository;

import edu.architect_711.words.entities.db.GroupEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface GroupRepository extends JpaRepository<GroupEntity, Long> {
    default GroupEntity safeFindById(Long id) {
        return findById(id).orElseThrow(() -> new EntityNotFoundException("Group not found with id: " + id));
    }

    @Query(
            nativeQuery = true,
            value = """
            select * from
                word_groups
            limit
                :size
            offset
                :offset
            """
    )
    List<GroupEntity> paginatedFind(final Long size, final Long offset);

    @Query(
            nativeQuery = true,
            value = """
            select
                word_groups.words_ids
            from
                word_groups
            where
                word_groups.id = :id
            limit
                :size
            offset
                :offset
            """
    )
    Set<Long> findPaginatedWordsIds(long id, Integer size, long offset);
}
