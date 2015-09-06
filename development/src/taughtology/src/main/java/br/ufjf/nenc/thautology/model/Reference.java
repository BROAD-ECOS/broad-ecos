package br.ufjf.nenc.thautology.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Reference<T extends Entity> {

    private final Class<T>  type;
    private final String id;


    private Reference(Class<T> type, String id) {
        this.type = type;
        this.id = id;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Entity> Reference<T> to(T entity) {
        Class<T> clazz = (Class<T>) entity.getClass();
        return new Reference<T>(clazz, entity.getId());
    }
}
