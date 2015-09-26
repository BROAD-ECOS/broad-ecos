package br.ufjf.nenc.thautology.service;

import br.ufjf.nenc.broadecos.api.model.Course;
import br.ufjf.nenc.thautology.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.google.common.base.Preconditions.checkArgument;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course save(Course course) {
        checkArgument(course != null);
        return courseRepository.save(course);
    }
}
