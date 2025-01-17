package edu.architect_711.words.model.mapper;

import edu.architect_711.words.model.dto.WordDto;
import edu.architect_711.words.model.entity.Person;
import edu.architect_711.words.model.entity.Word;
import edu.architect_711.words.model.entity.WordLanguage;

public interface WordMapper extends Mapper<WordDto, Word> {
    @Override
    default WordDto toDto(Word word) {
        return new WordDto(
                word.getId(),
                word.getPerson().getId(),
                word.getTitle(),
                word.getWordTranslation(),
                word.getWordDescription(),
                word.getLanguage().getTitle(),
                word.getLocalDateTime());
    }

    /**
     * Maps word dto to entity
     *
     * @param dto word dto
     * @param args first MUST be a person entity, second is WordLanguage
     * @return Word entity
     * */
    @Override
    default Word toEntity(WordDto dto, Object... args) {
        return toEntity(dto, (Person) args[0], (WordLanguage) args[1]);
    }

    default Word toEntity(WordDto wordDto, Person person, WordLanguage language) {
        return new Word(
                person,
                wordDto.getTitle(),
                wordDto.getWordTranslation(),
                wordDto.getWordDescription(),
                language
        );
    }

}
