package edu.architect_711.words.model.mapper;

import edu.architect_711.words.model.dto.PersonDto;
import edu.architect_711.words.model.entity.Person;

import java.util.function.Consumer;

public interface PersonMapper extends Mapper<PersonDto, Person> {

    @Override
    default Person toEntity(PersonDto personDto) {
        return new Person(
                personDto.getId(),
                personDto.getUsername(),
                personDto.getPassword(),
                personDto.getRole()
        );
    }

    default Person toEntity(PersonDto personDto, Consumer<PersonDto> modifier) {
        Person person = toEntity(personDto);
        modifier.accept(personDto);

        return person;
    }

    @Override
    default PersonDto toDto(Person person) {
        return new PersonDto(
                person.getId(),
                person.getUsername(),
                person.getPassword(),
                person.getRole()
        );
    }

}
