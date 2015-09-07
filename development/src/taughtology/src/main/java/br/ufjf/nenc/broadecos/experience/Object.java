package br.ufjf.nenc.broadecos.experience;

import lombok.*;

import java.net.URI;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Object {

    private String objectType;

    @NonNull
    private URI id;

    private Definition definition;

}
