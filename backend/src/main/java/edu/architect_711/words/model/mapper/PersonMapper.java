package edu.architect_711.words.model.mapper;

import edu.architect_711.words.model.dto.PersonDto;
import edu.architect_711.words.model.entity.Person;

public class PersonMapper {
    public static Person toPerson(final PersonDto personDto) {
        return new Person(
                personDto.getUsername(),
                personDto.getPassword()
        );
    }

    public static PersonDto toDto(final Person person) {
        return new PersonDto(
                person.getUsername(),
                person.getPassword()
        );
    }
}
