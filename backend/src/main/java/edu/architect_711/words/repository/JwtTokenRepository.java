package edu.architect_711.words.repository;

import edu.architect_711.words.entities.db.JwtTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JwtTokenRepository extends JpaRepository<JwtTokenEntity, Long> {
    @Modifying // because https://stackoverflow.com/a/58037702/22276970
    @Query(nativeQuery = true, value = """
            DELETE FROM token USING account
            WHERE token.account_id = account.id
            AND account.username = :username;
            """)
    void deleteAllByUsername(@Param("username") String username);

    Optional<JwtTokenEntity> findByRefreshToken(String refreshToken);

    Optional<JwtTokenEntity> findByAccessToken(String accessToken);
}
