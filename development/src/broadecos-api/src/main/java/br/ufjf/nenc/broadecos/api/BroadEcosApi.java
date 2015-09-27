package br.ufjf.nenc.broadecos.api;

import br.ufjf.nenc.broadecos.api.experience.ExperienceStatement;
import br.ufjf.nenc.broadecos.api.model.Course;
import br.ufjf.nenc.broadecos.api.model.ParticipantProfile;
import br.ufjf.nenc.broadecos.api.model.PlatformInfo;
import br.ufjf.nenc.broadecos.api.model.Reference;

import java.util.List;

public interface BroadEcosApi {

    ParticipantProfile getParticipant();

    PlatformInfo getPlatFormInfo();

    Course getCurrentCourse();

    List<ParticipantProfile> getCurrentCourseParticipants();

    Reference sendExperience(ExperienceStatement statement);
}
