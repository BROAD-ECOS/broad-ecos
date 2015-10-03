package br.ufjf.nenc.broadecos.rankr.model;


import br.ufjf.nenc.broadecos.api.experience.ExperienceStatement;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.constraints.NotNull;

import static java.lang.String.*;


@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@ToString
public class Conquest extends Entity{

    private static final String TITLE_FMT = "%s %s %s";

    @NotNull
    private ExperienceStatement statement;

    @DBRef
    @NotNull
    private User user;

    public Double getPoints() {
        return statement.getResult().getScore().getRaw();
    }

    public String getTitle() {
        return format(TITLE_FMT, user.getFullName(), statement.getVerb().getDisplay().get("pt-BR"), statement.getObject().getDefinition().getName().get("pt-BR"));
    }

}
