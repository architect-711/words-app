package edu.architect_711.words.startup;

import edu.architect_711.words.model.dto.PersonAuthorities;
import edu.architect_711.words.model.dto.PersonDto;
import edu.architect_711.words.model.dto.PersonRole;
import edu.architect_711.words.model.dto.WordLanguage;
import edu.architect_711.words.model.entity.Authorities;
import edu.architect_711.words.model.entity.Person;
import edu.architect_711.words.model.entity.Word;
import edu.architect_711.words.model.mapper.PersonMapper;
import edu.architect_711.words.repository.AuthoritiesRepository;
import edu.architect_711.words.repository.PersonRepository;
import edu.architect_711.words.repository.WordsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component @RequiredArgsConstructor @Slf4j @Transactional
public class DatabaseInitFiller implements ApplicationRunner {
    private final PersonRepository personRepository;
    private final AuthoritiesRepository authoritiesRepository;
    private final WordsRepository wordsRepository;

    private static final List<PersonDto> TEST_PEOPLE = List.of(
        new PersonDto("test1", "1234"),
        new PersonDto("test2", "password")
    );

    @Override
    public void run(ApplicationArguments args) throws Exception {
        fillDatabase();

        log.info("Test database entities saved: ");
        List.of(wordsRepository, personRepository, authoritiesRepository).forEach(this::logSavedData); 
    }

    private void logSavedData(final JpaRepository<?, Long> repository) {
        repository.findAll().forEach(data -> log.info("--- Saved entity: {}", data));
    }

    private void fillDatabase() {
        for (final PersonDto person : TEST_PEOPLE) {
            final Person savedPerson = savePerson(person);

            saveAuthorities(savedPerson.getId());
            saveWord(savedPerson.getId());
        }
    }

    private Person savePerson(final PersonDto personDto) {
        return personRepository.save(PersonMapper.toPerson(personDto));
    }

    private Authorities saveAuthorities(final Long id) {
        final Person savedPerson = getReferencedPersonById(id); 

        return authoritiesRepository.save(new Authorities(
            savedPerson, 
            savedPerson.getUsername(), 
            new PersonAuthorities[] {PersonAuthorities.READ, PersonAuthorities.DELETE, PersonAuthorities.UPDATE},
            PersonRole.USER
        ));  
    }

    private Word saveWord(final Long id) {
        final Person savedPerson = getReferencedPersonById(id); 

        final Word wordToBeSaved = new Word(
            savedPerson,
            "test_word_" + savedPerson.getUsername(),
            WordLanguage.ENGLISH
        );

        return wordsRepository.save(wordToBeSaved);
    }

    private Person getReferencedPersonById(final Long id) {
        return personRepository.getReferenceById(id);
    }
}
