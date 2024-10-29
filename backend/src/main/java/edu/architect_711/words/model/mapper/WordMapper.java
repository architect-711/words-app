package edu.architect_711.words.model.mapper;

import edu.architect_711.words.model.dto.WordDto;
import edu.architect_711.words.model.entity.Person;
import edu.architect_711.words.model.entity.Word;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

public class WordMapper {
    public static Word toEntity(final WordDto wordDto, final Person person) {
        return new Word(person, wordDto.getTitle(), wordDto.getLanguage());
    }

    public static WordDto toDto(final Word word) {
        return new WordDto(word.getId(), word.getPerson().getId(), word.getTitle(), word.getLanguage());
    }
}
