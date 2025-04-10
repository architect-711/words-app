package edu.architect_711.words.service.word;

import edu.architect_711.words.controller.service.WordService;
import edu.architect_711.words.entities.db.LanguageEntity;
import edu.architect_711.words.entities.db.WordEntity;
import edu.architect_711.words.entities.dto.WordDto;
import edu.architect_711.words.entities.mapper.WordMapper;
import edu.architect_711.words.repository.LanguageRepository;
import edu.architect_711.words.repository.WordRepository;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class DefaultWordService implements WordService {
    private final WordRepository wordRepository;
    private final LanguageRepository languageRepository;

    private final static WordMapper wordMapper = new WordMapper();

    @Override
    public ResponseEntity<List<WordDto>> read(Integer size, Integer page) {
        final List<WordEntity> foundWordEntities = wordRepository.findAll(PageRequest.of(page, size)).getContent();

        return ResponseEntity.ok(foundWordEntities.stream().map(wordMapper::toDto).toList());
    }

    @Override
    @Validated({Default.class, WordDto.OnCreate.class})
    public ResponseEntity<WordDto> create(@Valid WordDto wordDto) {
        final LanguageEntity languageEntity = languageRepository.safeFindByTitle(wordDto.getLanguage());

        return buildOkResponse(wordRepository.save(
                wordMapper.toEntity(wordDto, languageEntity)
        ));
    }

    @Override
    @Validated({Default.class, WordDto.OnCreated.class})
    public ResponseEntity<WordDto> update(@Valid WordDto wordDto) {
        final WordEntity wordEntity = wordRepository.safeFindWordByTitle(wordDto.getTitle());

        LanguageEntity languageEntity = languageRepository.safeFindByTitle(wordDto.getLanguage());
        languageEntity.setTitle(wordDto.getTitle());

        wordEntity.setTitle(wordDto.getTitle());
        wordEntity.setLanguageEntity(languageEntity);
        wordEntity.setTranslation(wordDto.getTranslation());
        wordEntity.setDescription(wordDto.getDescription());

        return buildOkResponse(wordRepository.save(wordEntity));
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        wordRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }

    private ResponseEntity<WordDto> buildOkResponse(WordEntity wordEntity) {
        return ResponseEntity.ok(wordMapper.toDto(wordEntity));
    }

}
