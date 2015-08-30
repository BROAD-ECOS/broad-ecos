package br.ufjf.nenc.thautology.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class Achievement extends Entity {

    @DBRef
    private Answer relatedAnswer;

    @NotNull
    private Long value;
}
