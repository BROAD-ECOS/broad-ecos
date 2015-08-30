package br.ufjf.nenc.thautology.provider;

import br.ufjf.nenc.broadecos.BroadEcosApi;
import br.ufjf.nenc.broadecos.Context;
import br.ufjf.nenc.broadecos.model.Course;
import br.ufjf.nenc.broadecos.model.ParticipantProfile;
import br.ufjf.nenc.thautology.model.CurrentUser;
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
