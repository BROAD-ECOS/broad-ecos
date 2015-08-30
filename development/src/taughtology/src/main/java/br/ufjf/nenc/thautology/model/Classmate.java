package br.ufjf.nenc.thautology.model;

import br.ufjf.nenc.broadecos.model.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
@AllArgsConstructor
public class Classmate extends Entity {

    @DBRef
    private Course course;

    @DBRef
    private User user;

}
