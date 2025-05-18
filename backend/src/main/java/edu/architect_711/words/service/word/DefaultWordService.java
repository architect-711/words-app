package edu.architect_711.words.service.word;

import edu.architect_711.words.entities.db.LanguageEntity;
import edu.architect_711.words.entities.db.WordEntity;
import edu.architect_711.words.entities.dto.BaseGroupDto;
import edu.architect_711.words.entities.dto.WordDto;
import edu.architect_711.words.entities.mapper.WordMapper;
import edu.architect_711.words.repository.LanguageRepository;
import edu.architect_711.words.repository.WordRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static edu.architect_711.words.entities.mapper.WordMapper.toDto;
import static edu.architect_711.words.entities.mapper.WordMapper.toEntity;
import static edu.architect_711.words.service.OffsetCalculator.offset;

@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class DefaultWordService implements WordService {
    private final WordRepository wordRepository;
    private final LanguageRepository languageRepository;

    // TODO there was @Transactional before
    @Override
    public List<WordDto> getAll(@NonNull Integer page, @NonNull Integer size) {
        return toDto(wordRepository.findAllPaginated(size, offset(size, page)));
    }

    @Override
    public List<WordDto> search(
            @NonNull Integer page,
            @NonNull Integer size,
            @NonNull String title,
            @NonNull String language
    ) {
        final int OFFSET = offset(size, page);
        final QueriedWordsSearcher queriedWordsSearcher = new QueriedWordsSearcher(wordRepository, OFFSET, size, title, language);

        queriedWordsSearcher.search();

        return queriedWordsSearcher.getFound().stream().map(WordMapper::toDto).toList();
    }

    @Override
    public WordDto getById(@NonNull Long id) {
        return wordRepository
                .findById(id)
                .map(WordMapper::toDto)
                .orElse(null);
    }

    @Override
    public void delete(@NonNull List<Long> ids) {
        ids.forEach(wordRepository::deleteById);
    }

    @Override
    public void delete(@NonNull Long id) {
        wordRepository.deleteById(id);
    }

    @Override
    @Validated({Default.class, WordDto.OnCreate.class})
    public @NonNull WordDto save(@Valid @NonNull WordDto wordDto) throws EntityExistsException {
        final LanguageEntity languageEntity = languageRepository.safeFindByTitle(wordDto.getLanguage());

        final WordEntity wordEntity = toEntity(wordDto, languageEntity);
        wordEntity.setUseCases(getValidUseCases(wordDto.getUseCases()));

        return toDto(wordRepository.save(wordEntity));
    }

    @Override
    @Validated({Default.class, WordDto.OnCreated.class})
    public @NonNull WordDto update(@Valid @NonNull WordDto wordDto) throws EntityNotFoundException {
        final WordEntity wordEntity = wordRepository.safeFindWordById(wordDto.getId());

        if (!wordDto.getLanguage().equals(wordEntity.getLanguage())) {
            final LanguageEntity languageEntity = languageRepository.safeFindByTitle(wordDto.getLanguage());

            wordEntity.setLanguageEntity(languageEntity);
        }

        wordEntity.setTitle(wordDto.getTitle());
        wordEntity.setTranslations(wordDto.getTranslations());
        wordEntity.setTranscription(wordDto.getTranscription());
        wordEntity.setDescription(wordDto.getDescription());
        wordEntity.setUseCases(getValidUseCases(wordDto.getUseCases()));

        return toDto(wordRepository.save(wordEntity));
    }

    @Override
    public List<BaseGroupDto> getGroupsTitles(@NonNull Long wordId) throws EntityNotFoundException {
        if (!wordRepository.existsById(wordId)) {
            throw new EntityNotFoundException("Word with id " + wordId + " not found");
        }

        return wordRepository.findWordGroupTitles(wordId);
    }

    /**
     * The {@code useCases} in {@link WordDto} might be null or blank,
     * so get rid of blank lines and return an empty list if it's null
     * @param raw the raw useCases came from the DTO
     * @return not null {@link List} of use cases, might be empty
     */
    @NonNull
    private static List<String> getValidUseCases(final List<String> raw) {
        return raw == null ? List.of() : raw.stream().filter(w -> w != null && !w.isBlank()).toList();
    }

}
