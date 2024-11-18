package edu.architect_711.words.service.utils;

import edu.architect_711.words.model.dto.WordDto;
import edu.architect_711.words.model.entity.Person;
import edu.architect_711.words.model.entity.Word;
import edu.architect_711.words.model.mapper.WordMapper;
import edu.architect_711.words.repository.PersonRepository;
import edu.architect_711.words.repository.WordsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component @RequiredArgsConstructor
public class WordsSaver {
    private final WordsRepository wordsRepository;
    private final PersonRepository personRepository;

    public List<WordDto> save(final List<WordDto> wordDtos) {
        final List<WordDto> savedWordDtos = new ArrayList<>();

        for (final WordDto wordDto : wordDtos) {
            final Person linkedPerson = personRepository.findById(wordDto.getUserId()).orElseThrow(() -> new IllegalArgumentException("No person with such id"));

            if (wordsRepository.findByTitle(wordDto.getTitle()).isPresent())
                continue;

            final Word savedWord = wordsRepository.save(WordMapper.toEntity(wordDto, linkedPerson));

            savedWordDtos.add(WordMapper.toDto(wordsRepository.getReferenceById(savedWord.getId())));
        }

        return savedWordDtos;
    }

}
