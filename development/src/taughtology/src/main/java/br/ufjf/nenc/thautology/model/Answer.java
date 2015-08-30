package br.ufjf.nenc.thautology.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.constraints.NotNull;


@Getter
@Setter
@ToString
public class Answer extends Entity {

    @DBRef
    @NonNull
    @NotNull
    private Question question;

    @DBRef
    @NonNull
    @NotNull
    private User user;

    @NonNull
    @NotNull
    private Boolean answeredOption;

    @NonNull
    @NotNull
    private Boolean correct;


}
