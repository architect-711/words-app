package edu.architect_711.words.unit;

import edu.architect_711.words.model.entity.Person;
import edu.architect_711.words.model.entity.Word;
import edu.architect_711.words.repository.PersonRepository;
import edu.architect_711.words.repository.WordsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static edu.architect_711.words.model.mapper.PersonMapper.fromListToPerson;
import static edu.architect_711.words.unit.UnitTestEntitiesConfiguration.getTestPeople;
import static edu.architect_711.words.unit.UnitTestEntitiesConfiguration.getTestWords;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class WordsRepositoryTest {

    @Autowired private WordsRepository wordsRepository;
    @Autowired private PersonRepository personRepository;

    @BeforeEach
    public void setUp() {
        wordsRepository.deleteAll();
    }

    @Test
    public void saveWord() {
        final Word word = getWordToBeSaved();
        final Word savedWord = wordsRepository.save(word);

        assertThat(savedWord).isNotNull();
        assertThat(wordsRepository.findByTitle(word.getTitle())).isPresent();
    }

    @Test
    public void deleteWord() {
        final Word savedWord = wordsRepository.save(getWordToBeSaved());

        wordsRepository.deleteById(savedWord.getId());

        assertThat(wordsRepository.findByTitle(savedWord.getTitle())).isEmpty();
    }

    @Test
    public void updateTitle() {
        final Word savedWord = wordsRepository.save(getWordToBeSaved());

        savedWord.setTitle("another title");
        wordsRepository.save(savedWord);

        assertThat(wordsRepository.findByTitle("another title")).isPresent();
    }

    @Test
    public void shouldThrowException__uniqueFieldViolation() {
        wordsRepository.save(getWordToBeSaved());

        assertThrows(DataIntegrityViolationException.class, () -> wordsRepository.save((getWordToBeSaved())));
    }

    private Word getWordToBeSaved() {
       final Word word =  getTestWords().getFirst();
       word.setPerson(getFirstPerson());

       return word;
    }

    private Person getFirstPerson() {
        final Optional<Person> firstPerson = personRepository.findFirst();

        return firstPerson.orElseGet(() -> personRepository.save(fromListToPerson(getTestPeople()).getFirst()));
    }
}
