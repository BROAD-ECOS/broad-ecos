package br.ufjf.nenc.thautology.event;

import br.ufjf.nenc.broadecos.api.Context;
import br.ufjf.nenc.thautology.model.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class EntityCreatedEvent<T extends Entity> {

    @Getter private final T entity;
    @Getter private final Context context;
    protected EntityCreatedEvent(T entity, Context context) {
        this.entity = entity;
        this.context = context;
    }

}
