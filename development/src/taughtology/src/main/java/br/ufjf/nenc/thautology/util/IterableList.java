package br.ufjf.nenc.thautology.util;


import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkArgument;

public class IterableList<T> extends AbstractList<T> {

    private final List<T> elements;

    public IterableList(Iterable<T> iterable) {
        checkArgument(iterable != null);

        List<T> elements = new ArrayList<>();
        iterable.iterator().forEachRemaining(elements::add);
        this.elements = elements;
    }


    @Override
    public T get(int index) {
        return elements.get(index);
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        elements.forEach(action);
    }

    @Override
    public Spliterator<T> spliterator() {
        return elements.spliterator();
    }

    @Override
    public Stream<T> stream() {
        return elements.stream();
    }

    @Override
    public Stream<T> parallelStream() {
        return elements.parallelStream();
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public boolean removeIf(Predicate<? super T> filter) {
        return elements.removeIf(filter);
    }

    @Override
    public void replaceAll(UnaryOperator<T> operator) {
        elements.replaceAll(operator);
    }

    @Override
    public void sort(Comparator<? super T> c) {
        elements.sort(c);
    }
}
