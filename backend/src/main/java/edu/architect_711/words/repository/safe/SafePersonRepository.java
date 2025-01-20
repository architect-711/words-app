package edu.architect_711.words.repository.safe;

import edu.architect_711.words.model.entity.Person;
import edu.architect_711.words.repository.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface SafePersonRepository extends PersonRepository {

    default Person findPersonById(Long id) {
        return findById(id).orElseThrow(() -> new EntityNotFoundException("Person not found with id: " + id));
    }

    default Person findPersonByUsername(String username) {
        return findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Person not found with username: " + username));
    }

}
