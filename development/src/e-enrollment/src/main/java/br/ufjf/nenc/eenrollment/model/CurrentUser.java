package br.ufjf.nenc.eenrollment.model;

import br.ufjf.nenc.broadecos.api.Context;
import br.ufjf.nenc.broadecos.api.model.ParticipantProfile;
import lombok.*;

import java.io.Serializable;


@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class CurrentUser implements Serializable {

    static final long serialVersionUID = 1L;

    private ParticipantProfile user;

    private Context context;

}
