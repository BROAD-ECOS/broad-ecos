package br.ufjf.nenc.broadecos.api.experience;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ExperienceStatement {

    private String id;

    @NonNull
    private Actor actor;

    @NonNull
    private Verb verb;

    @NonNull
    private Object object;

    private Context context;

    private Result result;

    private Actor authority;

}
