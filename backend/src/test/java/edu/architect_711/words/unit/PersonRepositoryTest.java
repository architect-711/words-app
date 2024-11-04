package edu.architect_711.words.unit;

import edu.architect_711.words.model.entity.Person;
import edu.architect_711.words.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PersonRepositoryTest {
    @Autowired
    private PersonRepository personRepository;

    private static final List<Person> testPeople = List.of(
            new Person("username1", "password"),
            new Person("username2", "password")
    );

    @BeforeEach
    public void setUp() {
        personRepository.deleteAll();
    }

    @Test
    public void saveAll() {
        List<Person> savedPeople = personRepository.saveAll(testPeople);

        assertThat(savedPeople).isNotEmpty();
        assertThat(savedPeople).isNotNull();

        for (final Person savedPerson : savedPeople)
            assertThat(savedPerson.getId()).isNotNull();
    }

    @Test
    public void deleteOne() {
        final Person savedPerson = save(testPeople.getFirst());

        personRepository.deleteById(savedPerson.getId());
        final Person deletedPerson = personRepository.findByUsername(testPeople.getFirst().getUsername()).orElse(null);

        assertThat(deletedPerson).isNull();
    }

    @Test
    public void updateOne() {
        final Person savedPerson = save(testPeople.getFirst());

        assertThat(personRepository.findByUsername(savedPerson.getUsername())).isPresent();

        savedPerson.setPassword("1234");

        personRepository.save(savedPerson);

        final Optional<Person> foundUpdatedByIdOptional = personRepository.findById(savedPerson.getId());
        assertThat(foundUpdatedByIdOptional).isPresent();

        final Person foundUpdatedById = foundUpdatedByIdOptional.get();
        assertThat(foundUpdatedById.getPassword()).isEqualTo("1234");

    }

    private Person save(final Person person) {
        return personRepository.save(person);
    }
}
