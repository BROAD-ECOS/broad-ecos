package br.ufjf.nenc.thautology.provider;

import br.ufjf.nenc.broadecos.api.BroadEcosApi;
import br.ufjf.nenc.broadecos.api.BroadEcosApiProvider;
import br.ufjf.nenc.broadecos.api.Context;
import br.ufjf.nenc.broadecos.api.model.Course;
import br.ufjf.nenc.broadecos.api.model.ParticipantProfile;
import br.ufjf.nenc.thautology.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CurrentCourseProvider {

    @Autowired
    private final BroadEcosApiProvider broadEcosApiProvider;

    @Autowired
    private final UserService userService;

    @Autowired
    public CurrentCourseProvider(BroadEcosApiProvider broadEcosApiProvider, UserService userService) {
        this.broadEcosApiProvider = broadEcosApiProvider;
        this.userService = userService;
    }

    // @Cacheable(cacheResolver = "TOKEN_CACHE")
    public Course currentCourse(Context context) {
        return broadEcosApiProvider
                .withContext(context)
                .getCurrentCourse();
    }

}
