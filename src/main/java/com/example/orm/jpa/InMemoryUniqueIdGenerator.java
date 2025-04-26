package com.example.orm.jpa;

import java.util.UUID;
import java.util.function.Function;

public class InMemoryUniqueIdGenerator<T> implements UniqueIdGenerator<T> {

    private final Function<UUID, T> factory;

    public InMemoryUniqueIdGenerator(Function<UUID, T> factory) {
        this.factory = factory;
    }

    @Override
    public T getNextUniqueId() {
        return factory.apply(UUID.randomUUID());
    }
}
