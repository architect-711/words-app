package edu.architect_711.words.repository;

import edu.architect_711.words.model.entity.Word;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WordsRepository extends JpaRepository<Word, Long> {
    Optional<Word> findByTitle(final String title);
}
