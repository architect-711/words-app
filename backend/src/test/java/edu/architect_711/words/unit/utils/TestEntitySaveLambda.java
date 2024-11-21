package edu.architect_711.words.unit.utils;

// E is an entity, D is it's DTO
public interface TestEntitySaveLambda<E, D> {
    // The amount of all entities are the same, so index specifies the current dto index in cycle
    E save(final D dto, final int INDEX);
}
