package edu.architect_711.words.unit.repository;

import edu.architect_711.words.model.dto.PersonDto;
import edu.architect_711.words.model.entity.Person;
import edu.architect_711.words.model.mapper.PersonMapper;
import edu.architect_711.words.repository.PersonRepository;
import edu.architect_711.words.startup.EnvConfigurationLoader;
import edu.architect_711.words.unit.utils.TestEntitySaver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static edu.architect_711.words.model.mapper.PersonMapper.fromListToPerson;
import static edu.architect_711.words.unit.configuration.UnitTestEntitiesConfiguration.getTestPeopleDTOs;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PersonRepositoryTest {
    @Autowired
    private PersonRepository personRepository;

    private final TestEntitySaver<Person, PersonDto> testEntitySaver = new TestEntitySaver<>(getTestPeopleDTOs(), this::save);

    @BeforeAll
    public static void loadEnv() {
        EnvConfigurationLoader.loadEnvConfiguration();
    }

    @BeforeEach
    public void setUp() {
        personRepository.deleteAll();
    }

    @Test
    public void saveAll() {
        List<Person> savedPeople = testEntitySaver.saveAll(); // personRepository.saveAll(getMappedTestPeople());

        assertThat(savedPeople).isNotEmpty();
        assertThat(savedPeople).isNotNull();

        for (final Person savedPerson : savedPeople)
            assertThat(savedPerson.getId()).isNotNull();
    }

    @Test
    public void deleteOne() {
        final Person savedPerson = testEntitySaver.saveAll().getFirst();

        personRepository.deleteById(savedPerson.getId());
        final Person deletedPerson = personRepository.findByUsername(savedPerson.getUsername()).orElse(null);

        assertThat(deletedPerson).isNull();
    }

    @Test
    public void updateOne() {
        final String NEW_WORD_PASSWORD = "1234";
        final Person savedPerson = testEntitySaver.saveAll().getFirst();

        assertThat(personRepository.findByUsername(savedPerson.getUsername())).isPresent();

        savedPerson.setPassword(NEW_WORD_PASSWORD);
        personRepository.save(savedPerson);

        final Person foundUpdatedById = personRepository.findById(savedPerson.getId()).orElseThrow();
        assertThat(foundUpdatedById.getPassword()).isEqualTo(NEW_WORD_PASSWORD);
    }

    @Test
    public void shouldThrowException__violatesUniqueConstraint() {
        final List<Person> savedPeople = testEntitySaver.saveAll();

        assertThrows(DataIntegrityViolationException.class, () -> personRepository.save(PersonMapper.toPerson(getTestPeopleDTOs().getFirst())));
    }

    private Person save(final PersonDto dto, final int INDEX) {
        return personRepository.save(fromListToPerson(getTestPeopleDTOs()).get(INDEX));
    }
}
