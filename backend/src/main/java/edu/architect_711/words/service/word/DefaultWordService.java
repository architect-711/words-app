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

        return ResponseEntity.ok(entityListToDto(foundWordEntities));
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
        final WordEntity wordEntity = wordRepository.safeFindWordById(wordDto.getId());

        if (!wordDto.getLanguage().equals(wordEntity.getLanguage())) {
            final LanguageEntity languageEntity = languageRepository.safeFindByTitle(wordDto.getLanguage());

            wordEntity.setLanguageEntity(languageEntity);
        }

        wordEntity.setTitle(wordDto.getTitle());
        wordEntity.setTranslation(wordDto.getTranslation());
        wordEntity.setDescription(wordDto.getDescription());

        return buildOkResponse(wordRepository.save(wordEntity));
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        wordRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<WordDto>> findByTitle(String title) {
        return ResponseEntity.ok(entityListToDto(wordRepository.findByTitleApproximates(title)));
    }

    @Override
    public ResponseEntity<List<WordDto>> findByLang(final String lang) {
        return ResponseEntity.ok(entityListToDto(wordRepository.findByLang(lang)));
    }

    private ResponseEntity<WordDto> buildOkResponse(WordEntity wordEntity) {
        return ResponseEntity.ok(wordMapper.toDto(wordEntity));
    }

    private static List<WordDto> entityListToDto(final List<WordEntity> entities) {
        return entities.stream().map(wordMapper::toDto).toList();
    }

}
