package br.ufjf.nenc.broadecos.rankr.service;

import br.ufjf.nenc.broadecos.api.BroadEcosApi;
import br.ufjf.nenc.broadecos.api.Context;
import br.ufjf.nenc.broadecos.api.model.Course;
import br.ufjf.nenc.broadecos.api.model.PlatformInfo;
import br.ufjf.nenc.broadecos.rankr.model.PlatformCourse;
import br.ufjf.nenc.broadecos.rankr.repository.PlatformCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Supplier;

@Service
public class BroadService {

    private final BroadEcosApi broadEcosApi;

    private final PlatformCourseRepository platformCourseRepository;

    @Autowired
    public BroadService(BroadEcosApi broadEcosApi, PlatformCourseRepository platformCourseRepository) {
        this.broadEcosApi = broadEcosApi;
        this.platformCourseRepository = platformCourseRepository;
    }

    public PlatformInfo getPlatformInfo(Context context){
        return broadEcosApi.withContext(context).getPlatFormInfo();
    }

    public Course getCurrentCourse(Context context) {
        return broadEcosApi.withContext(context).getCurrentCourse();
    }

    public PlatformCourse retrieveOrCreate(String platform, Course course) {

        Optional<PlatformCourse> platformCourse = platformCourseRepository.findByPlatformAndCourseId(platform, course.getId());

        Supplier<PlatformCourse> platformCourseSupplier = () -> platformCourseRepository.save(new PlatformCourse(platform, course));

        return platformCourse.orElseGet(platformCourseSupplier);
    }
}
