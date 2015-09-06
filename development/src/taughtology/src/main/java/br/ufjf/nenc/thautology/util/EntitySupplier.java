package br.ufjf.nenc.thautology.util;

import java.util.Optional;
import java.util.function.Function;

public class EntitySupplier<ID, ENTITY>{

    private final Optional<ID> id;
    private final Function<ID, Optional<ENTITY>> supplier;

    public EntitySupplier(Optional<ID> id, Function<ID, Optional<ENTITY>> supplier) {
        this.id = id;
        this.supplier = supplier;
    }

    public Optional<ENTITY> supply() {
        Optional<ENTITY> entity;
        if (id.isPresent()) {
            entity = supplier.apply(id.get());
        } else {
            entity = Optional.empty();
        }
        return entity;
    }
}
