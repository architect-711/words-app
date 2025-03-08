package edu.architect_711.words.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.architect_711.words.entities.db.JwtTokenEntity;

import java.util.Optional;

public interface JwtTokenRepository extends JpaRepository<JwtTokenEntity, Long> {
    @Query(nativeQuery = true, value = """
            WITH token AS (
                SELECT * FROM token
            )
            UPDATE token
            SET is_active = false
            WHERE person_id = :id;
            """)
    void deactivateAllByPersonId(Long id);

    @Query(nativeQuery = true, value = """
            DELETE FROM token USING account
            WHERE token.account_id = account.id
            AND account.username = :username;
                            """)
    void deleteAllByUsername(String username);

    Optional<JwtTokenEntity> findByRefreshToken(String refreshToken);

    Optional<JwtTokenEntity> findByAccessToken(String accessToken);
}
