package br.ufjf.nenc.broadecos.api;

import br.ufjf.nenc.broadecos.api.experience.ExperienceRequest;
import br.ufjf.nenc.broadecos.api.experience.ExperienceResponse;
import br.ufjf.nenc.broadecos.api.experience.ExperienceStatement;
import br.ufjf.nenc.broadecos.api.model.Course;
import br.ufjf.nenc.broadecos.api.model.ParticipantProfile;
import br.ufjf.nenc.broadecos.api.model.PlatformInfo;
import br.ufjf.nenc.broadecos.api.model.Reference;

import java.util.List;
import java.util.Optional;

public interface BroadEcosApi {

    ParticipantProfile getCurrentParticipant();

    Optional<ParticipantProfile> getParticipant(String id);

    PlatformInfo getPlatFormInfo();

    Course getCurrentCourse();

    List<ParticipantProfile> getCurrentCourseParticipants();

    List<ParticipantProfile> getAllParticipants();

    Reference sendExperience(ExperienceStatement statement);

    ExperienceResponse getExperience(ExperienceRequest experienceRequest);

    <T> T get(String url, Class<T> returnType);

    <T> List<T> getList(String url, Class<T> returnType);

    <T> T post(String url, T content);

}
