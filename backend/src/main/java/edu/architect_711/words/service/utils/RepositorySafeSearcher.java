package edu.architect_711.words.service.utils;

import edu.architect_711.words.model.entity.Person;
import edu.architect_711.words.model.entity.Word;
import edu.architect_711.words.model.entity.WordLanguage;
import edu.architect_711.words.repository.PersonRepository;
import edu.architect_711.words.repository.WordLanguagesRepository;
import edu.architect_711.words.repository.WordsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Supplier;

@Component @RequiredArgsConstructor
public class RepositorySafeSearcher implements ServiceThrowingBlanks {
    private final PersonRepository personRepository;
    private final WordLanguagesRepository wordLanguagesRepository;
    private final WordsRepository wordsRepository;

    public Person findPersonById(Long id) {
        return build(personRepository.findById(id), personNotFoundById(id));
    }

    public WordLanguage findWordLanguageByTitle(String title) {
        return build(wordLanguagesRepository.findByTitle(title), wordLanguageNotFoundByTitle(title));
    }

    public Word findWordById(Long id) {
        return build(wordsRepository.findById(id), wordNotFoundById(id));
    }

    private static <T> T build(Optional<T> action, Supplier<RuntimeException> handler) {
        return action.orElseThrow(handler);
    }
}
