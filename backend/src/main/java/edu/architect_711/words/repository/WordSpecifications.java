package edu.architect_711.words.repository;

import edu.architect_711.words.entities.db.WordEntity;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;

public class WordSpecifications {

    // I can't vouch for that
    @NonNull
    public static Specification<WordEntity> containingTitle(@NonNull final String title) {
        return containingToken("title", title);
    }

    @NonNull
    public static Specification<WordEntity> containingToken(@NonNull final String token, @NonNull final String value) {
        return (root, _, cb) -> cb.equal(root.get(token), value);
    }

}
