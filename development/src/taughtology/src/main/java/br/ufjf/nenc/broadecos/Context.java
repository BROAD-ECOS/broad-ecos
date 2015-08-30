package br.ufjf.nenc.broadecos;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Builder
@ToString
@Getter
@EqualsAndHashCode
public class Context implements Serializable {

    private static final long serialVersionUID = 1L;

    @lombok.NonNull
    private final String token;

    @lombok.NonNull
    private final String platform;

}
