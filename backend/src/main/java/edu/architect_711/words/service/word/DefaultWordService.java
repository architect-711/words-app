package edu.architect_711.words.service.word;

import edu.architect_711.words.controller.service.WordService;
import edu.architect_711.words.entities.db.AccountEntity;
import edu.architect_711.words.entities.db.LanguageEntity;
import edu.architect_711.words.entities.db.WordEntity;
import edu.architect_711.words.entities.dto.WordDto;
import edu.architect_711.words.entities.mapper.WordMapper;
import edu.architect_711.words.repository.AccountRepository;
import edu.architect_711.words.repository.LanguageRepository;
import edu.architect_711.words.repository.WordRepository;
import edu.architect_711.words.security.token.JwtAuthenticationToken;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class DefaultWordService implements WordService {
    private final WordRepository wordRepository;
    private final AccountRepository personRepository;
    private final LanguageRepository languageRepository;

    private final static WordMapper wordMapper = new WordMapper();

    @Override
    public ResponseEntity<List<WordDto>> read(Integer size, Integer page) {
        final JwtAuthenticationToken auth = safeGetAuth();
        final List<WordEntity> foundWordEntities = wordRepository.findAllPaginatedById(auth.getPrincipal().getId(), (long) size, (long) page);

        return ResponseEntity.ok(foundWordEntities.stream().map(wordMapper::toDto).toList());
    }

    @Override
    @Validated({Default.class, WordDto.OnCreate.class})
    public ResponseEntity<WordDto> create(@Valid WordDto wordDto) {
        long wordUserId = wordDto.getUserId();

        if (!Objects.equals(wordUserId, safeGetAuth().getPrincipal().getId()))
            throw new IllegalArgumentException("You can't save word and link it with other user");

        final AccountEntity personEntity = personRepository.safeFindById(wordUserId);
        final LanguageEntity languageEntity = languageRepository.safeFindByTitle(wordDto.getLanguage());

        return buildOkResponse(wordRepository.save(
                wordMapper.toEntity(wordDto, personEntity, languageEntity)
        ));
    }

    @Override
    @Validated({Default.class, WordDto.OnCreated.class})
    public ResponseEntity<WordDto> update(@Valid WordDto wordDto) {
        final JwtAuthenticationToken auth = safeGetAuth();

        if (!Objects.equals(wordDto.getUserId(), auth.getPrincipal().getId()))
            throw new IllegalArgumentException("You can't update another user's word.");

        final WordEntity wordEntity = wordRepository.safeFindWordById(wordDto.getId());

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
        final JwtAuthenticationToken auth = safeGetAuth();
        final WordEntity wordEntity = wordRepository.safeFindWordById(id);

        if (!Objects.equals(wordEntity.getUserId(), auth.getPrincipal().getId()))
            throw new IllegalArgumentException("You can't delete another user word."); // TODO unless you have rights...
        wordRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }

    private ResponseEntity<WordDto> buildOkResponse(WordEntity wordEntity) {
        return ResponseEntity.ok(wordMapper.toDto(wordEntity));
    }

    private JwtAuthenticationToken safeGetAuth() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if ( ! (authentication instanceof JwtAuthenticationToken) )
            throw new UnsupportedOperationException("Authentication object type mismatch");

        return (JwtAuthenticationToken) authentication;
    }

}
