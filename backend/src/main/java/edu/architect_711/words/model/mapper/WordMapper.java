package edu.architect_711.words.model.mapper;

import edu.architect_711.words.model.dto.WordDto;
import edu.architect_711.words.model.entity.Person;
import edu.architect_711.words.model.entity.Word;
import edu.architect_711.words.model.entity.WordLanguage;

public interface WordMapper {
    default Word wordDtoToEntity(WordDto wordDto, Person person, WordLanguage language) {
        return new Word(person, wordDto.getTitle(), language);
    }

    default WordDto wordEntityToDto(Word word) {
        return new WordDto(word.getId(), word.getPerson().getId(), word.getTitle(), word.getLanguage().getTitle(), word.getLocalDateTime());
    }
}
