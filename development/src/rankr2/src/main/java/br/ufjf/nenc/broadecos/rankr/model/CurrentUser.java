package br.ufjf.nenc.broadecos.rankr.model;

import br.ufjf.nenc.broadecos.api.Context;
import lombok.*;

import java.io.Serializable;


@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CurrentUser implements Serializable {

    static final long serialVersionUID = 1L;

    private User user;

    private Context context;

}
