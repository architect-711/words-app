package edu.architect_711.words.model.mapper;

import edu.architect_711.words.model.dto.TokenDto;
import edu.architect_711.words.model.entity.Person;
import edu.architect_711.words.model.entity.Token;

public interface TokenMapper {
    default TokenDto toDto(Token token) {
        return new TokenDto(
                token.getAccessToken(),
                token.getRefreshToken(),
                token.isLoggedOut()
        );
    }

    default Token toEntity(TokenDto dto, Person person) {
        return new Token(
                dto.accessToken(),
                dto.refreshToken(),
                dto.isLoggedOut(),
                person
        );
    }
}
