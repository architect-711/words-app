package edu.architect_711.words.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.architect_711.words.model.entity.Authorities;


public interface AuthoritiesRepository extends JpaRepository<Authorities, Long> {
    @Query(
        value = "SELECT * FROM authorities WHERE api_key = ?1",
        nativeQuery = true
    )
    Optional<Authorities> findAuthorityByApiKey(final String apiKey);
    @Query(
        nativeQuery = true,
        value = "SELECT authorities.api_key FROM authorities LIMIT 1"
    )
    Optional<String> findRandomApiKey();
    @Query(
            value = "SELECT api_key FROM authorities WHERE user_id = ?1",
            nativeQuery = true
    )
    Optional<String> findApiKeyByUserId(final Long userId);
}
