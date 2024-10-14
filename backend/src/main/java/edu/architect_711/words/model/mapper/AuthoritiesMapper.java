package edu.architect_711.words.model.mapper;

import edu.architect_711.words.model.dto.AuthoritiesDto;
import edu.architect_711.words.model.entity.Authorities;

public class AuthoritiesMapper {
    public static AuthoritiesDto toDto(final Authorities authorities) {
        return new AuthoritiesDto(authorities.getUserId(), authorities.getApi_key(), authorities.getAuthorities(), authorities.getRole());
    } 

    public static Authorities toEntity(final AuthoritiesDto dto) {
        return new Authorities(dto.getUserId(), dto.getApiKey(), dto.getAuthorities(), dto.getRole());
    }
}
