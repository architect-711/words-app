package edu.architect_711.words.unit;

import edu.architect_711.words.model.dto.PersonDto;
import edu.architect_711.words.model.dto.WordLanguage;
import edu.architect_711.words.model.entity.Word;

import java.util.List;

public class UnitTestEntitiesConfiguration {
    public static List<PersonDto> getTestPeople() {
        return List.of(
                new PersonDto("username1", "password"),
                new PersonDto("username2", "password")
        );
    }

    public static List<Word> getTestWords() {
        return List.of(
                new Word(null, "test1", WordLanguage.ENGLISH),
                new Word(null, "test2", WordLanguage.ENGLISH)
        );
    }
}
