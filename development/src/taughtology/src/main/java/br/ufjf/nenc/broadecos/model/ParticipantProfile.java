package br.ufjf.nenc.broadecos.model;

import lombok.Data;
import lombok.ToString;

@Data
public class ParticipantProfile {

    private String id;
    private String email;
    private String firstName;
    private String userName;
    private String lastName;
    private String picture;

    private String institution;
    private String departament;

    private String locale;

}
