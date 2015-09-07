package br.ufjf.nenc.thautology.model;

import br.ufjf.nenc.broadecos.model.Course;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Classmate extends Entity {

    @DBRef
    private Course course;

    @DBRef
    private User user;

}
