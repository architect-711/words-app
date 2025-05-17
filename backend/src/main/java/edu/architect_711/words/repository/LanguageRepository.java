package edu.architect_711.words.repository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import edu.architect_711.words.entities.db.LanguageEntity;

import java.util.Optional;

public interface LanguageRepository extends JpaRepository<LanguageEntity, Long> {
    Optional<LanguageEntity> findByTitle(String title);

    default LanguageEntity safeFindByTitle(String language) throws EntityNotFoundException {
        return this.findByTitle(language).orElseThrow(() -> new EntityNotFoundException("Language not found with title: " + language));
    }
}
