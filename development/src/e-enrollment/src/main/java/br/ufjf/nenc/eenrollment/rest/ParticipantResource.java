package br.ufjf.nenc.eenrollment.rest;

import br.ufjf.nenc.broadecos.api.Context;
import br.ufjf.nenc.broadecos.api.model.ParticipantProfile;
import br.ufjf.nenc.eenrollment.service.ParticipantService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "/participants")
@Log4j
public class ParticipantResource {

    private final ParticipantService participantService;

    @Autowired
    public ParticipantResource(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @RequestMapping(path = "/able-to-enroll", method = GET)
    public Set<ParticipantProfile> ableToEnroll(Context context) {
        return participantService.getNonEnrolled(context);
    }

}
