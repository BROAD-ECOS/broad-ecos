package br.ufjf.nenc.thautology.util;

import java.util.Optional;

@FunctionalInterface
public interface OptionalSupplier<T> {

    Optional<T> get();

}
