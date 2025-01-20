package edu.architect_711.words.repository.safe;

import edu.architect_711.words.model.entity.WordLanguage;
import edu.architect_711.words.repository.WordLanguagesRepository;
import jakarta.persistence.EntityNotFoundException;

public interface SafeWordLanguageRepository extends WordLanguagesRepository {

    default WordLanguage findWordLanguageByTitle(String language) {
        return findByTitle(language).orElseThrow(() -> new EntityNotFoundException("Word language not found with title: " + language));
    }
}
