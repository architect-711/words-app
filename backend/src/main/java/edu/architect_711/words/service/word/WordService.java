package edu.architect_711.words.service.word;

//@Service @RequiredArgsConstructor @Slf4j @Validated
public class WordService {
//    private final WordRepository wordRepository;
//    private final PersonRepository personRepository;
//    private final LanguageRepository languageRepository;
//
//    public ResponseEntity<List<WordDto>> read(Integer size, Integer page) {
//        final List<WordEntity> foundWordEntities = safeWordRepository.findAll(PageRequest.of(page, size)).getContent(); // TODO findAll???????????????????????????
//
//        return ResponseEntity.ok(foundWordEntities.stream().map(this::toDto).toList());
//    }
//
//    @Validated(WordValidationGroups.Create.class)
//    public ResponseEntity<WordDto> create(@Valid WordDto wordDto) {
//        PersonEntity personEntity = personRepository.safeFindById(wordDto.getUserId()); // TODO fix security flaw
//        LanguageEntity languageEntity = languageRepository.safeFindByTitle(wordDto.getLanguage());
//
//        return buildOkResponse(safeWordRepository.save(toEntity(wordDto, personEntity, languageEntity)));
//    }
//
//    public ResponseEntity<WordDto> update(@Valid WordDto wordDto) {
//        LanguageEntity languageEntity = safeWordLanguageRepository.findWordLanguageByTitle(wordDto.getLanguage()); // TODO fix security flaw
//
//        WordEntity foundWordEntity = safeWordRepository.findWordById(wordDto.getId());
//
//        foundWordEntity.setTitle(wordDto.getTitle());
//        foundWordEntity.setLanguageEntity(languageEntity);
//        foundWordEntity.setWordTranslation(wordDto.getWordTranslation());
//        foundWordEntity.setWordDescription(wordDto.getWordDescription());
//
//        return buildOkResponse(safeWordRepository.save(foundWordEntity));
//    }
//
//    @PreAuthorize("#authentication.getPersonEntity().getId() == id")
//    public ResponseEntity<?> delete(Long id) { // TODO fix security flaw
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (!(authentication instanceof JwtAuthenticationToken jwtAuthenticationToken) || !Objects.equals(jwtAuthenticationToken.getPersonEntity().getId(), id)) {
//            wordRepository.deleteById(id);
//
//            return ResponseEntity.ok().build();
//        }
//        //403 fuck you
//    }
//
//    private ResponseEntity<WordDto> buildOkResponse(WordEntity wordEntity) {
//        return ResponseEntity.ok(toDto(wordEntity));
//    }

}
