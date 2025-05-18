package edu.architect_711.words.service.word;

import edu.architect_711.words.entities.db.WordEntity;
import edu.architect_711.words.repository.WordRepository;
import jakarta.validation.constraints.Min;
import lombok.NonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static edu.architect_711.words.service.OffsetCalculator.page;

public record QueriedWordsSearcher(@NonNull WordRepository wordRepository,
                                   @NonNull @Min(0) Integer offset,
                                   @NonNull @Min(0) Integer size,
                                   @NonNull String title,
                                   @NonNull String language
) {
    private static final Set<WordEntity> found = new HashSet<>();

    public void search() {
        found.clear();
        found.addAll(determine());
    }

    private List<WordEntity> determine() {
        final boolean bothNotBlank = !title.isBlank() && !language.isBlank();

        if (bothNotBlank) return findByAll();
        else if (!title.isBlank()) return findByTitleOnly();
        else if (!language.isBlank()) return findByLangOnly();
        return wordRepository.findAllPaginated(size, offset);
    }

    private List<WordEntity> findByAll() {
        return wordRepository
                .findAllByTitlePaginated(title, size, page(offset, size))
                .stream()
                .filter(word -> word.getLanguage().equals(language))
                .toList();
    }

    private List<WordEntity> findByTitleOnly() {
        return wordRepository.findAllByTitlePaginated(title, size, page(offset, size)); //findAll(containingTitle(title), PageRequest.of(page(offset, size), size)).getContent();
    }

    private List<WordEntity> findByLangOnly() {
        return wordRepository.findAllByLanguagePaginated(language, size, offset);
    }

    public Set<WordEntity> getFound() {
        return found;
    }
}
