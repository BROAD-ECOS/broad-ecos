package br.ufjf.nenc.eenrollment.provider;

import br.ufjf.nenc.broadecos.api.BroadEcosApiProvider;
import br.ufjf.nenc.broadecos.api.Context;
import br.ufjf.nenc.broadecos.api.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CurrentCourseProvider {

    @Autowired
    private final BroadEcosApiProvider broadEcosApiProvider;


    @Autowired
    public CurrentCourseProvider(BroadEcosApiProvider broadEcosApiProvider) {
        this.broadEcosApiProvider = broadEcosApiProvider;
    }

    public Course currentCourse(Context context) {
        return broadEcosApiProvider.withContext(context)
                .getCurrentCourse();
    }

}
