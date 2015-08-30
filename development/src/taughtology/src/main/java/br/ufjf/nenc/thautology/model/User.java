package br.ufjf.nenc.thautology.model;

import br.ufjf.nenc.broadecos.model.ParticipantProfile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class User extends Entity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String participantId;

    private String platform;

    private Level level;

    private ParticipantProfile participantProfile;

    public User(String id, String platform) {
        this.participantId = id;
        this.platform = platform;
        level = Level.EASY;
    }

}
