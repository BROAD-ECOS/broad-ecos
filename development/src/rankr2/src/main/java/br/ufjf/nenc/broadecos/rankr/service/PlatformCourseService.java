package br.ufjf.nenc.broadecos.rankr.service;

import br.ufjf.nenc.broadecos.rankr.model.PlatformCourse;
import br.ufjf.nenc.broadecos.rankr.repository.PlatformCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PlatformCourseService {

    private final PlatformCourseRepository platformCourseRepository;

    @Autowired
    public PlatformCourseService(PlatformCourseRepository platformCourseRepository) {
        this.platformCourseRepository = platformCourseRepository;
    }


    public List<PlatformCourse> all() {
        List<PlatformCourse>  platformCourses = new ArrayList<>();
        platformCourseRepository.findAll().forEach(platformCourses::add);
        return Collections.unmodifiableList(platformCourses);
    }

}
