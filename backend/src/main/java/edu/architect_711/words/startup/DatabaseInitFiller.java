package edu.architect_711.words.startup;

import edu.architect_711.words.model.dto.PersonAuthorities;
import edu.architect_711.words.model.dto.PersonRole;
import edu.architect_711.words.model.entity.Authorities;
import edu.architect_711.words.model.entity.Person;
import edu.architect_711.words.repository.AuthoritiesRepository;
import edu.architect_711.words.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component @RequiredArgsConstructor @Profile("dev") @Slf4j
public class DatabaseInitFiller implements ApplicationRunner {
    private final PersonRepository personRepository;
    private final AuthoritiesRepository authoritiesRepository;

    private static final List<Person> TEST_PEOPLE = List.of(
        new Person("test1", "1234"),
        new Person("test2", "password")
    );

    @Override
    public void run(ApplicationArguments args) throws Exception {
        fillDatabase();
        
        log.info("Saved in database values: ");
        personRepository.findAll().forEach(person -> log.info(person.toString()));
        authoritiesRepository.findAll().forEach(a -> log.info(a.toString()));
        log.info("Test database entities saved.");
    }

    private void fillDatabase() {
        for (final Person person : TEST_PEOPLE) {
            final Long createdPersonId = personRepository.save(person).getId();
            final Authorities preparedAuthority = new Authorities(
                    createdPersonId,
                    person.getUsername(),
                    new PersonAuthorities[] {PersonAuthorities.READ, PersonAuthorities.DELETE, PersonAuthorities.UPDATE},
                    PersonRole.USER
            );

            log.info("Saving authority: {}", preparedAuthority);

            authoritiesRepository.save(preparedAuthority);
        }
    }
}
