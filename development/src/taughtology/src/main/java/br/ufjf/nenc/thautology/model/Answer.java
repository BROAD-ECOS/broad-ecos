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
