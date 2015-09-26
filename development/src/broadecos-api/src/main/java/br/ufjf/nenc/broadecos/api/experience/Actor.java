package br.ufjf.nenc.broadecos.api.experience;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Actor {

    private String objectType;
    private String name;
    @NonNull
    private Account account;
}
