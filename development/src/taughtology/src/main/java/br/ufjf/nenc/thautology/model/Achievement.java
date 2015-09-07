package br.ufjf.nenc.thautology.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public abstract class Achievement<T> extends Entity {

    @DBRef
    @NotNull
    private User user;

    @NotNull
    private Long value;


    public abstract T getEntity();

}
