package br.ufjf.nenc.broadecos.rankr.job;

import br.ufjf.nenc.broadecos.api.model.Course;
import br.ufjf.nenc.broadecos.rankr.model.PlatformCourse;
import br.ufjf.nenc.broadecos.rankr.service.PlatformCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ConquestLoader {


    private final PlatformCourseService platformCourseService;

    private final ConcurrentHashMap<String, Set<Course>> courses = new ConcurrentHashMap<>();


    @Autowired
    public ConquestLoader(PlatformCourseService platformCourseService) {
        this.platformCourseService = platformCourseService;
    }


    @Scheduled(fixedRate = 10000)
    public void reportCurrentTime() {

        Map.Entry<String, Set<Course>> entry = courses.entrySet().iterator().next();



    }


    @Scheduled(fixedRate = 10000)
    public void updateCourses() {
        platformCourseService.all().stream()
                .filter((pc)-> !courses.containsKey(pc.getPlatform()))
                .forEach(this::addPlatformCourse);
    }

    public void addPlatformCourse(PlatformCourse platformCourse) {
        Set<Course> coursesForPlatform = courses.get(platformCourse.getPlatform());

        if (coursesForPlatform != null) {
            coursesForPlatform.add(platformCourse.getCourse());
        } else {
            coursesForPlatform = new HashSet<>();
        }

        courses.put(platformCourse.getPlatform(), coursesForPlatform);
    }

}
