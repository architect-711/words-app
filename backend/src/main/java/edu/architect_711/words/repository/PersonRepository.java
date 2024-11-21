package edu.architect_711.words.repository;

import edu.architect_711.words.model.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByUsername(final String username);
    @Query(
        nativeQuery = true,
        value = "SELECT users.id, users.username, users.password FROM authorities JOIN users ON authorities.user_id = users.id AND authorities.api_key = ?1"
    )
    Optional<Person> findPersonByApiKey(final String apiKey);

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM users LIMIT 1"
    )
    Optional<Person> findFirst();
}
