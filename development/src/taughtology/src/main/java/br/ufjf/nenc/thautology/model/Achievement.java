package br.ufjf.nenc.thautology.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public abstract class Achievement<T> extends Entity {

    @DBRef
    @NotNull
    private User user;

    @NotNull
    private Long value;


    public abstract T getEntity();

}
