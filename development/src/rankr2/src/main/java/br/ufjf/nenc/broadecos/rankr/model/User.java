package br.ufjf.nenc.broadecos.rankr.model;

import br.ufjf.nenc.broadecos.api.model.ParticipantProfile;
import com.google.common.base.Preconditions;
import lombok.*;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Date;

import static com.google.common.base.Preconditions.checkArgument;

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

    private ParticipantProfile participantProfile;

    private Long points = 0l;

    private Integer rating = 0;

    public User(String id, String platform) {
        this.participantId = id;
        this.platform = platform;

        final Date date = new Date();
        this.setLastUpdated(date);
        this.setCreated(date);
    }

    @JsonIgnore
    @XmlTransient
    public String getFullName() {
        return participantProfile.getFirstName() + " " + participantProfile.getLastName();
    }

    public void addPoints(Double points) {
        checkArgument(points != null);
        addPoints(points.longValue());
    }

    public void addPoints(Long points) {
        checkArgument(points != null);
        this.points =  points;
    }
}
