package edu.architect_711.words.service.group;

import edu.architect_711.words.entities.dto.GroupDto;
import jakarta.annotation.Nullable;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.NonNull;

import java.util.List;

public interface GroupService {
    /**
     * Get all groups paginated
     * @param size the size of a page
     * @param page the page
     * @return the list of found groups, might empty
     */
    @NonNull
    List<GroupDto> getAll(@NonNull @Min(0) Integer size, @NonNull @Min(0) Integer page);

    /**
     * Find Group by its id
     * @param id the id of the group
     * @return found group or null instead
     */
    @Nullable
    GroupDto getById(@NonNull @Min(0) Long id);

    /**
     * Save a new group
     * @param groupDto the group dto to be saved
     * @return saved group
     */
    @NonNull
    GroupDto save(@Valid GroupDto groupDto);

    /**
     * Update exiting group
     * @param groupDto group to be updated
     * @return updated group
     * throws {@link EntityNotFoundException} if the group you are trying to update
     * doesn't exist
     */
    @NonNull
    GroupDto update(@Valid GroupDto groupDto) throws EntityNotFoundException;

    /**
     * Get all words ids from a certain group
     * @param groupId the id of the group
     * @return the collection of words
     */
    @NonNull
    List<Long> getWordIds(@NonNull @Min(0) Long groupId, @NonNull @Min(0) Integer size, @NonNull @Min(0) Integer page);

    /**
     * Deletes a single group, ignores if it doesn't exist
     * @param id the id of the group to be deleted
     */
    void deleteById(@NonNull @Min(0) Long id);

}
