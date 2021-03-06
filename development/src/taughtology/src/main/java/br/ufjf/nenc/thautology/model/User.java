package br.ufjf.nenc.thautology.model;

import br.ufjf.nenc.broadecos.api.model.ParticipantProfile;
import lombok.*;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
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

    @JsonIgnore
    @XmlTransient
    public String getFullName() {
        return participantProfile.getFirstName() + " " + participantProfile.getLastName();
    }
}
