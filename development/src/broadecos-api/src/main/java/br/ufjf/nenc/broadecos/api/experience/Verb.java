package br.ufjf.nenc.broadecos.api.experience;

import lombok.*;

import java.net.URI;
import java.util.Map;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Verb {
    private URI id;
    private Map<String, String> display;
}
