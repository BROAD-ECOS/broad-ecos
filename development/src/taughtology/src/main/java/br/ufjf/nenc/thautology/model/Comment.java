package br.ufjf.nenc.thautology.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@ToString
public class Comment extends Entity {

    @DBRef
    @NotNull
    private User author;

    @NotNull
    private String message;

    @DBRef
    @NotNull
    private Question question;
}
