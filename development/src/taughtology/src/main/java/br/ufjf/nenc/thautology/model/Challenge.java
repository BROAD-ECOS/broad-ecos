package br.ufjf.nenc.thautology.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Challenge extends Entity {

    @DBRef
    private User challenged;

    @DBRef
    private User challenger;

    @DBRef
    private Question question;

    private Boolean met = Boolean.FALSE;

    private Boolean accepted = Boolean.FALSE;


}
