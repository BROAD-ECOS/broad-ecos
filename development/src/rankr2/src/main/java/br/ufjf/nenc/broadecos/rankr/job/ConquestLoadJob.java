package br.ufjf.nenc.broadecos.rankr.job;

import br.ufjf.nenc.broadecos.api.AuthRequest;
import br.ufjf.nenc.broadecos.api.BroadEcosApiProvider;
import br.ufjf.nenc.broadecos.api.model.Course;
import br.ufjf.nenc.broadecos.rankr.component.ConquestLoader;
import br.ufjf.nenc.broadecos.rankr.model.PlatformCourse;
import br.ufjf.nenc.broadecos.rankr.service.PlatformCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ConquestLoadJob {

    private final BroadEcosApiProvider broadEcosApiProvider;

    private final PlatformCourseService platformCourseService;

    private final ConquestLoader conquestLoader;

    private final ConcurrentHashMap<String, Set<Course>> courses = new ConcurrentHashMap<>();


    @Autowired
    public ConquestLoadJob(BroadEcosApiProvider broadEcosApiProvider, PlatformCourseService platformCourseService, ConquestLoader conquestLoader) {
        this.broadEcosApiProvider = broadEcosApiProvider;
        this.platformCourseService = platformCourseService;
        this.conquestLoader = conquestLoader;
    }


    @Scheduled(fixedRate = 30*1000, initialDelay = 1*60*1000)
    public void reportCurrentTime() {
        courses.forEach((platform, courses) ->
            courses.forEach((course) -> requestAuth(platform, course))
        );
    }

    private AuthRequest requestAuth(String platform, Course course) {
        final AuthRequest authRequest = broadEcosApiProvider.requestAuth(platform, course.getId());
        authRequest.whenReady(conquestLoader::load);
        return authRequest;
    }

    @Scheduled(fixedRate = 1*60*1000)
    public void updateCourses() {
        platformCourseService.all().stream()
                .filter((pc)-> !courses.containsKey(pc.getPlatform()))
                .forEach(this::addPlatformCourse);
    }

    public void addPlatformCourse(PlatformCourse platformCourse) {
        Set<Course> coursesForPlatform = courses.get(platformCourse.getPlatform());

        if (coursesForPlatform == null) {
            coursesForPlatform = new HashSet<>();
        }

        coursesForPlatform.add(platformCourse.getCourse());
        courses.put(platformCourse.getPlatform(), coursesForPlatform);
    }

}
