package edu.architect_711.words.repository;

import edu.architect_711.words.model.entity.WordLanguage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WordLanguagesRepository extends JpaRepository<WordLanguage, Long> {
    Optional<WordLanguage> findByTitle(String title);
}
