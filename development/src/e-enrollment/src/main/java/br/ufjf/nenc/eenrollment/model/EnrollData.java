package br.ufjf.nenc.eenrollment.model;

import br.ufjf.nenc.broadecos.api.Context;
import br.ufjf.nenc.broadecos.api.model.ParticipantProfile;
import lombok.Data;

import java.util.Set;

@Data
public class EnrollData {
    private String courseId;
    private Set<String> participantIds;
}
