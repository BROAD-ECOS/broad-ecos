package br.ufjf.nenc.eenrollment.service;

import br.ufjf.nenc.broadecos.api.Context;
import br.ufjf.nenc.broadecos.api.model.ParticipantProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ParticipantService {

    private final BroadService broadService;

    @Autowired
    public ParticipantService(BroadService broadService) {
        this.broadService = broadService;
    }

    public Set<ParticipantProfile> getNonEnrolled(Context context){
        Set<ParticipantProfile> profiles = new HashSet<>(broadService.getAllPlatformParticipants(context));
        profiles.removeAll(broadService.getCurrentCourseParticipants(context));
        return profiles;
    }

}
