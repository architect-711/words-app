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
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
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

        wordDto.setUseCases(getValidUseCases(wordDto.getUseCases()));

        return buildOkResponse(wordRepository.save(
                wordMapper.toEntity(wordDto, languageEntity)
        ));
    }

    private static List<String> getValidUseCases(final List<String> raw) {
        return raw == null ? List.of() : raw.stream().filter(w -> w != null && !w.isBlank()).toList();
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
        wordEntity.setUseCases(getValidUseCases(wordDto.getUseCases()));

        return buildOkResponse(wordRepository.save(wordEntity));
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        wordRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<WordDto>> findByTitle(String title) {
        return ResponseEntity.ok(entityListToDto(wordRepository.findByTitleApproximates(title, 5, 0)));
    }

    @Override
    public ResponseEntity<List<WordDto>> findByLang(final String lang) {
        return ResponseEntity.ok(entityListToDto(wordRepository.findPaginatedByLangAprx(lang, 5, 0)));
    }

    @Override
    public ResponseEntity<List<WordDto>> find(final int size, final int page,
        final String title, final String lang
    ) {
        final List<WordEntity> res = new ArrayList<>();

        if (!title.isBlank() && !lang.isBlank())
            res.addAll(wordRepository
                .findPaginatedByLangAprx(lang, size, page)
                .stream()
                .filter(osya -> osya.getTitle().contains(title))
                .toList()
            );
        else if (!title.isBlank())
            res.addAll(wordRepository.findByTitleApproximates(title, size, page));
        else if (!lang.isBlank())
            res.addAll(wordRepository.findPaginatedByLangAprx(lang, size, page));
        else
            return read(size, page);

        return ResponseEntity.ok().body(entityListToDto(res));
    }

    private ResponseEntity<WordDto> buildOkResponse(WordEntity wordEntity) {
        return ResponseEntity.ok(wordMapper.toDto(wordEntity));
    }

    private static List<WordDto> entityListToDto(final List<WordEntity> entities) {
        return entities.stream().map(wordMapper::toDto).toList();
    }

    public ResponseEntity<WordDto> getById(Long id) {
        return ResponseEntity.ok(wordMapper.toDto(wordRepository.safeFindWordById(id)));
    }
}
