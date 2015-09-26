package br.ufjf.nenc.thautology.provider;

import br.ufjf.nenc.broadecos.api.BroadEcosApi;
import br.ufjf.nenc.broadecos.api.Context;
import br.ufjf.nenc.broadecos.api.model.Course;
import br.ufjf.nenc.broadecos.api.model.ParticipantProfile;
import br.ufjf.nenc.thautology.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CurrentCourseProvider {

    @Autowired
    private final BroadEcosApi broadEcosApi;

    @Autowired
    private final UserService userService;

    @Autowired
    public CurrentCourseProvider(BroadEcosApi broadEcosApi, UserService userService) {
        this.broadEcosApi = broadEcosApi;
        this.userService = userService;
    }

    // @Cacheable(cacheResolver = "TOKEN_CACHE")
    public Course currentCourse(Context context) {
        return broadEcosApi
                .withContext(context)
                .getCurrentCourse();
    }

}
