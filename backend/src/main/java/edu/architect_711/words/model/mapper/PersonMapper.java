package edu.architect_711.words.model.mapper;

import edu.architect_711.words.model.dto.PersonDto;
import edu.architect_711.words.model.entity.Person;

import java.util.function.Consumer;

public interface PersonMapper extends Mapper<PersonDto, Person> {

    @Override
    default Person toEntity(PersonDto personDto) {
        return new Person(
                personDto.getUsername(),
                personDto.getPassword()
        );
    }

    default Person toEntity(PersonDto personDto, Consumer<Person> modifier) {
        Person person = toEntity(personDto);
        modifier.accept(person);

        return person;
    }

    @Override
    default PersonDto toDto(Person person) {
        return new PersonDto(
                person.getUsername(),
                person.getPassword()
        );
    }

}
