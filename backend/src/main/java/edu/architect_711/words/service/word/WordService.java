package edu.architect_711.words.service.word;

import edu.architect_711.words.entities.dto.BaseGroupDto;
import edu.architect_711.words.entities.dto.WordDto;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public interface WordService {
    /**
     * Return all words found in the database by pages
     * @param page the offset number from 0. Must be positive
     * @param size the size of the offset. Must be positive
     * @return {@code List<WordDto>} the list of word dtos
     */
    @NotNull
    List<WordDto> getAll(@NonNull @Min(0) Integer page, @NonNull @Min(0) Integer size);

    /**
     * Find some words by title and language (both are optional)
     * @param page offset number
     * @param size offset size
     * @param title word's title
     * @param language word's language
     * @return {@code Set<WordDto>} the found words, might be empty
     */
    @NotNull
    List<WordDto> search(@NonNull @Min(0) Integer page, @NonNull @Min(0) Integer size, @NonNull String title, @NonNull String language);

    /**
     * Find the word by id
     * @param id word's id
     * @return wrapped in the {@link Optional}, the found word, or null instead
     */
    @NotNull
    WordDto getById(@NonNull @Min(0) Long id);

    /**
     * Delete multiple words by their id. Ignore not found word.
     * @param ids the id of each words
     */
    void delete(@NonNull List<Long> ids);

    /**
     * Delete a single word by id, if not found - ignore
     * @param id the id of the word
     */
    void delete(@NonNull @Min(0) Long id);

    /**
     * Save new word
     * @param wordDto the word to be saved
     * @return {@link WordDto} the saved word
     * @throws EntityExistsException when trying to save the word violating unique constraints
     */
    @NonNull
    WordDto save(@NonNull @Valid WordDto wordDto) throws EntityExistsException;

    /**
     * Update already existing word
     * @param wordDto really existing word to be updated
     * @return updated word
     * @throws EntityExistsException if word wasn't find
     */
    @NonNull
    WordDto update(@NonNull @Valid WordDto wordDto) throws EntityNotFoundException;

    /**
     * Find the base info (see {@link BaseGroupDto} of groups where the word is located in
     * @param wordId the id of the word
     * @return the titles of the groups where the word is located in
     * @throws EntityExistsException if the word wasn't find
     */
    @NotNull
    List<BaseGroupDto> getGroupsTitles(@NonNull @Min(0) Long wordId) throws EntityNotFoundException;
}
