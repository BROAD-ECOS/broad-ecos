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
