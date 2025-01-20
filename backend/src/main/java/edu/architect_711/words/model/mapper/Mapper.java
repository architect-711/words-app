package edu.architect_711.words.model.mapper;

import edu.architect_711.words.exception.MethodNotImplementedException;

/**
 * Interface that should be implemented by all mappers
 * */
public interface Mapper<D, E> {
    MethodNotImplementedException exception = new MethodNotImplementedException("This method isn't implemented");

    default D toDto(E entity) {
        throw exception;
    }
    default D toDto(E entity, Object... args) {
        throw exception;
    }

    default E toEntity(D dto) {
        throw exception;
    }
    default E toEntity(D dto, Object... args) {
        throw exception;
    }
}
