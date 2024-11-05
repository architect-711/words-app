package edu.architect_711.words.unit.configuration;

import edu.architect_711.words.model.dto.*;

import java.util.List;

public class UnitTestEntitiesConfiguration {
    public static List<PersonDto> getTestPeople() {
        return List.of(
                new PersonDto("username1", "password"),
                new PersonDto("username2", "password")
        );
    }

    public static List<WordDto> getTestWords() {
        return List.of(
                new WordDto(null, null, "test1", WordLanguage.ENGLISH),
                new WordDto(null, null, "test2", WordLanguage.ENGLISH)
        );
    }

    public static List<AuthoritiesDto> getTestAuthoritiesDTOs() {
        return List.of(
                new AuthoritiesDto(null, null, new PersonAuthorities[] {PersonAuthorities.READ, PersonAuthorities.UPDATE, PersonAuthorities.DELETE}, PersonRole.ADMIN),
                new AuthoritiesDto(null, null, new PersonAuthorities[] {PersonAuthorities.READ}, PersonRole.USER)
        );
    }
}
