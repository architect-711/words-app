package edu.architect_711.words.model.mapper;

import edu.architect_711.words.model.dto.AuthoritiesDto;
import edu.architect_711.words.model.entity.Authorities;
import edu.architect_711.words.model.entity.Person;

public class AuthoritiesMapper {
    public static AuthoritiesDto toDto(final Authorities authorities) {
        return new AuthoritiesDto(authorities.getPerson().getId(), authorities.getApi_key(), authorities.getAuthorities(), authorities.getRole());
    } 

    public static Authorities toEntity(final AuthoritiesDto dto, final Person person) {
        return new Authorities(person, dto.getApiKey(), dto.getAuthorities(), dto.getRole());
    }
}
