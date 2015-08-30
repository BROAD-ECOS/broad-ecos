package br.ufjf.nenc.thautology.service;

import br.ufjf.nenc.broadecos.BroadEcosApi;
import br.ufjf.nenc.broadecos.model.Course;
import br.ufjf.nenc.broadecos.model.ParticipantProfile;
import br.ufjf.nenc.thautology.model.Classmate;
import br.ufjf.nenc.thautology.model.CurrentUser;
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

@Service
@Log4j
public class ClassmateService {

    private final BroadEcosApi broadEcosApi;

    private final ClassmateRepository classmateRepository;

    private final UserService userService;

    @Autowired
    public ClassmateService(BroadEcosApi broadEcosApi, ClassmateRepository classmateRepository, UserService userService) {
        this.broadEcosApi = broadEcosApi;
        this.classmateRepository = classmateRepository;
        this.userService = userService;
    }

    public List<Classmate> getClassmates(CurrentUser user, Course course) {

        List<Classmate> classmates = new ArrayList<>();

        List<ParticipantProfile> courseParticipants = getCurrentCourseParticipants(user);

        classmateRepository.deleteByUserParticipantProfileNotInAndCourse(courseParticipants, course);

        classmates.addAll(new IterableList<>(classmateRepository.findAllByCourse(course)));

        Map<String, Classmate> classmateByProfile = classmates.stream().collect(Collectors.toMap(
                classmate -> classmate.getUser().getParticipantProfile().getId(),
                classmate -> classmate
        ));

        courseParticipants.stream()
                .filter(participantProfile -> !classmateByProfile.containsKey(participantProfile.getId()))
                .map(userService::getUser)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(classmateUser -> classmateRepository.save(new Classmate(course, classmateUser)))
                .forEach(classmates::add);

        return classmates;
    }

    private List<ParticipantProfile> getCurrentCourseParticipants(CurrentUser user) {
        return broadEcosApi.withContext(user.getContext()).getCurrentCourseParticipants();
    }

}
