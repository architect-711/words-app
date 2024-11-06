package edu.architect_711.words.unit;

import edu.architect_711.words.model.dto.WordDto;
import edu.architect_711.words.model.entity.Person;
import edu.architect_711.words.model.entity.Word;
import edu.architect_711.words.model.mapper.WordMapper;
import edu.architect_711.words.repository.PersonRepository;
import edu.architect_711.words.repository.WordsRepository;
import edu.architect_711.words.unit.utils.TestEntitySaver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static edu.architect_711.words.model.mapper.PersonMapper.fromListToPerson;
import static edu.architect_711.words.unit.configuration.UnitTestEntitiesConfiguration.getTestPeopleDTOs;
import static edu.architect_711.words.unit.configuration.UnitTestEntitiesConfiguration.getTestWordsDTOs;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class WordsRepositoryTest {

    @Autowired private WordsRepository wordsRepository;
    @Autowired private PersonRepository personRepository;

    private final TestEntitySaver<Word, WordDto> testEntitySaver = new TestEntitySaver<>(getTestWordsDTOs(), this::save);

    @BeforeEach
    public void setUp() {
        wordsRepository.deleteAll();
    }

    @Test
    public void saveWord() {
        final List<Word> savedWords = testEntitySaver.saveAll();

        assertThat(savedWords).isNotEmpty();
        savedWords.forEach(word -> assertThat(wordsRepository.findByTitle(word.getTitle())).isPresent());
    }

    @Test
    public void deleteWord() {
        final Word savedWord = testEntitySaver.saveAll().getFirst();

        wordsRepository.deleteById(savedWord.getId());

        assertThat(wordsRepository.findByTitle(savedWord.getTitle())).isEmpty();
    }

    @Test
    public void updateTitle() {
        final Word savedWord = testEntitySaver.saveAll().getFirst();

        savedWord.setTitle("another title");
        wordsRepository.save(savedWord);

        assertThat(wordsRepository.findByTitle("another title")).isPresent();
    }

    @Test
    public void shouldThrowException__uniqueFieldViolation() {
        testEntitySaver.saveAll();

        assertThrows(DataIntegrityViolationException.class, () -> wordsRepository.save(WordMapper.toEntity(getTestWordsDTOs().getFirst(), getPersonByIdOrSave(1))));
    }

    private Word save(final WordDto dto, final int INDEX) {
        final Person person = getPersonByIdOrSave(INDEX);
        final Word word = WordMapper.toEntity(getTestWordsDTOs().get(INDEX), person);
        word.setPerson(person);

        return wordsRepository.save(word);
    }

    private Person getPersonByIdOrSave(final int INDEX) {
        final Optional<Person> firstPerson = personRepository.findByUsername(getTestPeopleDTOs().get(INDEX).getUsername());

        return firstPerson.orElseGet(() -> personRepository.save(fromListToPerson(getTestPeopleDTOs()).get(INDEX)));
    }
}
