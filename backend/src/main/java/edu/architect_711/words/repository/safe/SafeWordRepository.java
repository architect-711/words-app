package edu.architect_711.words.repository.safe;

import edu.architect_711.words.model.entity.Word;
import edu.architect_711.words.repository.WordsRepository;
import jakarta.persistence.EntityNotFoundException;

public interface SafeWordRepository extends WordsRepository {

    default Word findWordById(Long id) {
        return findById(id).orElseThrow(() -> new EntityNotFoundException("Word not found with id: " + id));
    }
}
