package edu.architect_711.words.model.mapper;

import edu.architect_711.words.model.dto.PersonDto;
import edu.architect_711.words.model.entity.Person;
import jakarta.validation.constraints.NotNull;

public interface PersonMapper {
    default Person personDtoToEntity(@NotNull PersonDto personDto) {
        return new Person(
                personDto.getUsername(),
                personDto.getPassword()
        );
    }

    default PersonDto personEntityToDto(@NotNull Person person) {
        return new PersonDto(
                person.getUsername(),
                person.getPassword()
        );
    }
}
