package br.ufjf.nenc.broadecos.experience;

import lombok.*;

import java.net.URI;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @NonNull
    private URI homePage;

    @NonNull
    private String name;
}
