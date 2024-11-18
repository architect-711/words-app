package edu.architect_711.words.unit.utils;

import java.util.ArrayList;
import java.util.List;

// E is an entity, D is it's DTO
public class TestEntitySaver<E, D> {
    private final List<D> dtos;
    private final TestEntitySaveLambda<E, D> saveFunction;

    public TestEntitySaver(List<D> dtos, TestEntitySaveLambda<E, D> saveFunction) {
        this.dtos = dtos;
        this.saveFunction = saveFunction;
    }

    public List<E> saveAll() {
        final ArrayList<E> saved = new ArrayList<>(dtos.size());

        for (int index = 0; index < dtos.size(); index++)
            saved.add(saveFunction.save(dtos.get(index), index));

        return saved;
    }

}
