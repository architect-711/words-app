package edu.architect_711.words.repository;

import edu.architect_711.words.model.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByAccessToken(String accessToken);

    @Query(
            nativeQuery = true,
            value = """
                    select tokens.id, tokens.access_token, tokens.refresh_token, tokens.is_logged_out, tokens.person_id from tokens
                    inner join person on tokens.person_id = person.id
                    where tokens.person_id = :id
                    and tokens.is_logged_out = false;
                    """
    )
    List<Token> findAllUnloggedTokensByPersonId(Long id);
}
