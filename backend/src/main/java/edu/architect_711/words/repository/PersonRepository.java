package edu.architect_711.words.repository;

import edu.architect_711.words.model.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByUsername(final String username);

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM users LIMIT 1"
    )
    Optional<Person> findFirst();
}
