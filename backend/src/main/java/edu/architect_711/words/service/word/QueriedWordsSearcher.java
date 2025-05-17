package edu.architect_711.words.service.word;

import edu.architect_711.words.entities.db.WordEntity;
import edu.architect_711.words.repository.WordRepository;
import jakarta.validation.constraints.Min;
import lombok.NonNull;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record QueriedWordsSearcher(@NonNull WordRepository wordRepository,
                                   @NonNull @Min(0) Integer offset,
                                   @NonNull @Min(0) Integer size,
                                   @NonNull String title,
                                   @NonNull String language
) {
    private static final Set<WordEntity> found = new HashSet<>();

    public void search() {
        final boolean bothNotBlank = !title.isBlank() && !language.isBlank();

        if (bothNotBlank)
            findByAll();
        else if (!title.isBlank())
            findByTitleOnly();
        else if (!language.isBlank())
            findByLangOnly();
    }

    private void findByAll() {
        found.addAll(wordRepository
                .findPaginatedByLangApproximately(language, offset, size)
                .stream()
                .filter(e -> e.getTitle().contains(title))
                .collect(Collectors.toSet())
        );
    }

    private void findByTitleOnly() {
        found.addAll(wordRepository
                .findByTitleApproximates(title, offset, size)
        );
    }

    private void findByLangOnly() {
        found.addAll(wordRepository
                .findPaginatedByLangApproximately(language, offset, size)
        );
    }

    public Set<WordEntity> getFound() {
        return found;
    }

}
