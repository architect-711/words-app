package edu.architect_711.words.repository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import edu.architect_711.words.entities.db.AccountEntity;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    Optional<AccountEntity> findByUsername(final String username);

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM account LIMIT 1"
    )
    Optional<AccountEntity> findFirst();

    default AccountEntity safeFindById(Long id) {
        return findById(id).orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + id));
    }

    default AccountEntity safeFindByUsername(String username) throws UsernameNotFoundException {
        return findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Account not found with username: " + username));
    }
}
