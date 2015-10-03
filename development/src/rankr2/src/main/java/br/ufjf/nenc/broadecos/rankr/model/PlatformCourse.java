package br.ufjf.nenc.broadecos.rankr.model;


import br.ufjf.nenc.broadecos.api.model.Course;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class PlatformCourse extends Entity {

    private String platform;

    private Course course;

    public PlatformCourse(String platform, Course course) {
        this.platform = platform;
        this.course = course;
        this.setCreated(new Date());
        this.setLastUpdated(new Date());
    }
}
