package br.ufjf.nenc.broadecos.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Maintainer {

    private final String email;
    private final String name;

}
