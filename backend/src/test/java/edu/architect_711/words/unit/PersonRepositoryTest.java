package edu.architect_711.words.unit;

import edu.architect_711.words.model.entity.Person;
import edu.architect_711.words.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static edu.architect_711.words.model.mapper.PersonMapper.fromListToPerson;
import static edu.architect_711.words.unit.UnitTestEntitiesConfiguration.getTestPeople;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PersonRepositoryTest {
    @Autowired
    private PersonRepository personRepository;

    @BeforeEach
    public void setUp() {
        personRepository.deleteAll();
    }

    @Test
    public void saveAll() {
        List<Person> savedPeople = personRepository.saveAll(getMappedTestPeople());

        assertThat(savedPeople).isNotEmpty();
        assertThat(savedPeople).isNotNull();

        for (final Person savedPerson : savedPeople)
            assertThat(savedPerson.getId()).isNotNull();
    }

    @Test
    public void deleteOne() {
        final Person savedPerson = saveFirst();

        personRepository.deleteById(savedPerson.getId());
        final Person deletedPerson = personRepository.findByUsername(savedPerson.getUsername()).orElse(null);

        assertThat(deletedPerson).isNull();
    }

    @Test
    public void updateOne() {
        final String NEW_WORD_PASSWORD = "1234";
        final Person savedPerson = saveFirst();

        assertThat(personRepository.findByUsername(savedPerson.getUsername())).isPresent();

        savedPerson.setPassword(NEW_WORD_PASSWORD);
        personRepository.save(savedPerson);

        final Person foundUpdatedById = personRepository.findById(savedPerson.getId()).orElseThrow();
        assertThat(foundUpdatedById.getPassword()).isEqualTo(NEW_WORD_PASSWORD);
    }

    @Test
    public void shouldThrowException__violatesUniqueConstraint() {
        personRepository.save(getMappedTestPeople().getFirst());

        assertThrows(DataIntegrityViolationException.class, () -> personRepository.save(getMappedTestPeople().getFirst()));
    }

    private Person saveFirst() {
        return personRepository.save(getMappedTestPeople().getFirst());
    }

    private List<Person> getMappedTestPeople() {
        return fromListToPerson(getTestPeople());
    }
}
