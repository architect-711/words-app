package edu.architect_711.words.entities.mapper;

import edu.architect_711.words.entities.db.AccountEntity;
import edu.architect_711.words.entities.db.JwtTokenEntity;
import edu.architect_711.words.entities.dto.JwtTokenDto;

public class JwtTokenMapper implements EntityMapper<JwtTokenEntity, JwtTokenDto> {
    @Override @Deprecated
    public JwtTokenEntity toEntity(JwtTokenDto dto) {
         alarm();
         return null;
    };

    public JwtTokenEntity toEntity(JwtTokenDto dto, boolean isActive, AccountEntity accountEntity) {
        return new JwtTokenEntity(
                dto.getAccessToken(),
                dto.getRefreshToken(),
                isActive,
                accountEntity
        );
    }

    @Override
    public JwtTokenDto toDto(JwtTokenEntity jwtTokenEntity) {
        return new JwtTokenDto(
                jwtTokenEntity.getAccessToken(),
                jwtTokenEntity.getRefreshToken()
        );
    }
}
