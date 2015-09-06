package br.ufjf.nenc.thautology.model;

import br.ufjf.nenc.broadecos.model.ParticipantProfile;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
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
