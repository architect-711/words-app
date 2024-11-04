package edu.architect_711.words.model.mapper;

import edu.architect_711.words.model.dto.PersonDto;
import edu.architect_711.words.model.entity.Person;

import java.util.List;

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

    public static List<Person> fromListToPerson(final List<PersonDto> personDtos) {
        return personDtos.stream().map(PersonMapper::toPerson).toList();
    }
}
