package edu.architect_711.words.unit.repository;

import edu.architect_711.words.model.dto.AuthoritiesDto;
import edu.architect_711.words.model.dto.PersonRole;
import edu.architect_711.words.model.entity.Authorities;
import edu.architect_711.words.model.entity.Person;
import edu.architect_711.words.model.mapper.AuthoritiesMapper;
import edu.architect_711.words.model.mapper.PersonMapper;
import edu.architect_711.words.repository.AuthoritiesRepository;
import edu.architect_711.words.repository.PersonRepository;
import edu.architect_711.words.service.utils.TestApiKeyGenerator;
import edu.architect_711.words.startup.EnvConfigurationLoader;
import edu.architect_711.words.unit.utils.TestEntitySaver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static edu.architect_711.words.unit.configuration.UnitTestEntitiesConfiguration.getTestAuthoritiesDTOs;
import static edu.architect_711.words.unit.configuration.UnitTestEntitiesConfiguration.getTestPeopleDTOs;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ComponentScan(basePackages = "edu.architect_711.words.service.utils")
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthoritiesRepositoryTest {
    @Autowired private AuthoritiesRepository authoritiesRepository;
    @Autowired private TestApiKeyGenerator testApiKeyGenerator;
    @Autowired private PersonRepository personRepository;

    private final TestEntitySaver<Authorities, AuthoritiesDto> testEntitySaver = new TestEntitySaver<>(getTestAuthoritiesDTOs(), this::save);

    @BeforeAll
    public static void loadEnv() {
        EnvConfigurationLoader.loadEnvConfiguration();
    }

    @BeforeEach
    public void cleanDatabase() {
        authoritiesRepository.deleteAll();
    }

    @Test
    public void saveAll() {
        final List<Authorities> savedAuthorities = testEntitySaver.saveAll();

        assertThat(savedAuthorities).isNotNull();

        savedAuthorities.forEach(authorities -> assertTrue(authoritiesRepository.findById(authorities.getId()).isPresent()));
    }

    @Test
    public void deleteById() {
        final Long savedOneId = testEntitySaver.saveAll().getFirst().getId();

        authoritiesRepository.deleteById(savedOneId);

        assertThat(authoritiesRepository.findById(savedOneId)).isEmpty();

    }

    @Test
    public void updateById() {
        final Authorities saved = testEntitySaver.saveAll().getFirst();

        final PersonRole role = saved.getRole().equals(PersonRole.USER) ? PersonRole.ADMIN : PersonRole.USER;
        saved.setRole(role);

        authoritiesRepository.save(saved);

        assertEquals(authoritiesRepository.findById(saved.getId()).orElseThrow().getRole(), role);
    }

    private Authorities save(final AuthoritiesDto dto, final int INDEX) {
        final Person person = personRepository.save(PersonMapper.toPerson(getTestPeopleDTOs().get(INDEX)));

        dto.setUserId(person.getId());
        dto.setApiKey(testApiKeyGenerator.generate());

        return authoritiesRepository.save(AuthoritiesMapper.toEntity(dto, person));
    }

}
