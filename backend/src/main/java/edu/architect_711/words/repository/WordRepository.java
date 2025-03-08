package edu.architect_711.words.repository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import edu.architect_711.words.entities.db.WordEntity;

import java.util.Optional;

public interface WordRepository extends JpaRepository<WordEntity, Long> {
    Optional<WordEntity> findByTitle(final String title);

    default WordEntity findWordById(Long id) {
        return findById(id).orElseThrow(() -> new EntityNotFoundException("WordEntity not found with id: " + id));
    }
}
