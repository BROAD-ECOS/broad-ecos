package br.ufjf.nenc.thautology.service;

import br.ufjf.nenc.broadecos.api.BroadEcosApi;
import br.ufjf.nenc.broadecos.api.BroadEcosApiProvider;
import br.ufjf.nenc.broadecos.api.model.Course;
import br.ufjf.nenc.broadecos.api.model.ParticipantProfile;
import br.ufjf.nenc.thautology.model.Classmate;
import br.ufjf.nenc.thautology.model.CurrentUser;
import br.ufjf.nenc.thautology.model.User;
import br.ufjf.nenc.thautology.repository.ClassmateRepository;
import br.ufjf.nenc.thautology.util.IterableList;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;

@Service
@Log4j
public class ClassmateService {

    private final BroadEcosApiProvider broadEcosApiProvider;

    private final ClassmateRepository classmateRepository;

    private final UserService userService;

    private final CourseService courseService;

    @Autowired
    public ClassmateService(BroadEcosApiProvider broadEcosApiProvider, ClassmateRepository classmateRepository, UserService userService, CourseService courseService) {
        this.broadEcosApiProvider = broadEcosApiProvider;
        this.classmateRepository = classmateRepository;
        this.userService = userService;
        this.courseService = courseService;
    }

    public List<Classmate> getClassmates(CurrentUser user, Course course) {
        checkArgument(user != null);
        checkArgument(course != null);

        List<Classmate> classmates = new ArrayList<>();

        List<ParticipantProfile> courseParticipants = getCurrentCourseParticipants(user);

        classmates.addAll(new IterableList<>(classmateRepository.findAllByCourseId(course.getId())));

        Map<String, Classmate> classmateByProfile = classmates.stream().collect(Collectors.toMap(
                classmate -> classmate.getUser().getParticipantProfile().getId(),
                classmate -> classmate
        ));

        courseParticipants.stream()
                .filter(participantProfile -> !classmateByProfile.containsKey(participantProfile.getId()))
                .map(userService::getUser)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(classmateUser -> save(course, classmateUser))
                .forEach(classmates::add);

        classmates.removeIf(classmate -> classmate.getUser().getId().equals(user.getUser().getId()));

        return classmates;
    }

    private Classmate save(Course course, User classmateUser) {
        course = courseService.save(course);
        return classmateRepository.save(new Classmate(course, classmateUser));
    }

    private List<ParticipantProfile> getCurrentCourseParticipants(CurrentUser user) {
        return broadEcosApiProvider.withContext(user.getContext()).getCurrentCourseParticipants();
    }

}
