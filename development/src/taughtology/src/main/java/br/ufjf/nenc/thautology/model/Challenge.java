package br.ufjf.nenc.thautology.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
public class Challenge extends Entity {

    @DBRef
    private User challenged;

    @DBRef
    private User challenger;

    @DBRef
    private Question question;

    private Boolean met = false;

}
