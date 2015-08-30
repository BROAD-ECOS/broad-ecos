package br.ufjf.nenc.thautology.event;

import br.ufjf.nenc.thautology.model.Answer;
import br.ufjf.nenc.thautology.model.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class EntityCreatedEvent<T extends Entity> {

    @Getter private final T entity;


    protected EntityCreatedEvent(T entity) {
        this.entity = entity;
    }

}
